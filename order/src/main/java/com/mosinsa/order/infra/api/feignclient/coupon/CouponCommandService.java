package com.mosinsa.order.infra.api.feignclient.coupon;

import com.mosinsa.order.infra.api.ResponseResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponCommandService {

    private final CouponClient couponClient;

    public ResponseResult<CouponResponse> useCoupon(Map<String, Collection<String>> headers, String couponId) {

		return ResponseResult.execute(() -> {
			String body = null;
			try {
				body = HttpClient.newBuilder().build()
						.send(HttpRequest.newBuilder().uri(new URI("http://127.0.0.1:8000/coupon-service/coupons/"+couponId)).POST(HttpRequest.BodyPublishers.noBody()).build(), HttpResponse.BodyHandlers.ofString()).body();
//				return new ObjectMapper().readValue(body, CouponResponse.class);
			} catch (IOException | InterruptedException | URISyntaxException e) {
				throw new RuntimeException(e);
			}
		});
    }

    public ResponseResult<CouponResponse> cancelCoupon(Map<String, Collection<String>> headers, String couponId) {
        return ResponseResult.execute(() -> couponClient.cancelCoupon(headers, couponId));
    }
}
