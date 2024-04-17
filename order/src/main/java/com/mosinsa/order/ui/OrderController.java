package com.mosinsa.order.ui;

import com.mosinsa.order.command.application.CancelOrderService;
import com.mosinsa.order.command.application.PlaceOrderService;
import com.mosinsa.order.command.application.dto.CreateOrderDto;
import com.mosinsa.order.common.ex.OrderRollbackException;
import com.mosinsa.order.infra.feignclient.HeaderConst;
import com.mosinsa.order.infra.feignclient.coupon.CouponCommandService;
import com.mosinsa.order.infra.feignclient.coupon.CouponQueryService;
import com.mosinsa.order.infra.feignclient.coupon.CouponResponse;
import com.mosinsa.order.infra.feignclient.product.ProductCommandService;
import com.mosinsa.order.query.application.dto.OrderDetail;
import com.mosinsa.order.ui.request.CreateOrderRequest;
import com.mosinsa.order.ui.request.MyOrderProduct;
import com.mosinsa.order.ui.response.BaseResponse;
import com.mosinsa.order.ui.response.GlobalResponseEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/orders")
@RequiredArgsConstructor
public class OrderController {

    private final PlaceOrderService placeOrderService;

    private final CancelOrderService cancelOrderService;

    private final CouponQueryService couponQueryService;

    private final CouponCommandService couponCommandService;

    private final ProductCommandService productCommandService;

    @PostMapping("/orderConfirm")
    public ResponseEntity<BaseResponse> orderConfirm(@RequestBody CreateOrderRequest orderRequest, HttpServletRequest request) {

        // 주문 확인서 return
        // 멱등키 추가 전달해서 멱등성보장 + 데이터 변조 검사

        return null;

    }

    @PostMapping("/order")
    public ResponseEntity<BaseResponse> orders(@RequestBody CreateOrderRequest orderRequest, HttpServletRequest request) {

        Map<String, Collection<String>> authMap = getAuthMap(request);
        CouponResponse coupon = couponQueryService.couponCheck(authMap, orderRequest.couponId())
                .onSuccess(() -> couponCommandService.useCoupon(authMap, orderRequest.couponId()).orElseThrow())
                .orElse(CouponResponse.empty());

        productCommandService.orderProduct(authMap, orderRequest).orElseThrow();

        try {
            CreateOrderDto createOrderDto = new CreateOrderDto(orderRequest.customerId(),
                    coupon, orderRequest.shippingInfo(), orderRequest.myOrderProducts());

            OrderDetail orderDto = placeOrderService.order(createOrderDto, c -> c.getCouponResponse().available());

            return GlobalResponseEntity.success(HttpStatus.CREATED, orderDto);
        } catch (Exception e) {
            log.error("order fail => rollback product stock, coupon use");

            try {
                productCommandService.cancelOrderProduct(authMap, orderRequest.myOrderProducts());
                couponCommandService.cancelCoupon(authMap, coupon.couponId());
            } catch (Exception ex) {
                //TODO: kafka 이용 후처리

            }
            throw new OrderRollbackException(e);
        }

    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<BaseResponse> cancelOrders(@PathVariable String orderId, HttpServletRequest request) {

        Map<String, Collection<String>> authMap = getAuthMap(request);

        OrderDetail cancelOrder = cancelOrderService.cancelOrder(orderId);

        try {
            productCommandService.cancelOrderProduct(authMap,
                    cancelOrder.getOrderProducts().stream().map(
                            op -> new MyOrderProduct(op.getProductId(), op.getPrice(), op.getQuantity())).toList());
            couponCommandService.cancelCoupon(authMap, cancelOrder.getCouponId());
        } catch (Exception e) {
            //TODO: kafka 이용 후처리
            // 주문 취소 성공, 외부 api 호출(상품 수량 증가) 실패
            // 호출을 주문 취소 이후에하는 이유는 취소에 주문 실패했을 때 롤백요청하는 사이에 타 사용자가 수량 가져갈 가능성
        }

        return GlobalResponseEntity.success(orderId);
    }

    private Map<String, Collection<String>> getAuthMap(HttpServletRequest request) {
        Map<String, Collection<String>> authMap = new HashMap<>();
        String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(auth)) {
            authMap.put(HttpHeaders.AUTHORIZATION, List.of(auth));
        }
        String token = request.getHeader(HeaderConst.REFRESH_TOKEN.getName());
        if (StringUtils.hasText(token)) {
            authMap.put(HeaderConst.REFRESH_TOKEN.getName(), List.of(token));
        }
        return authMap;
    }

}
