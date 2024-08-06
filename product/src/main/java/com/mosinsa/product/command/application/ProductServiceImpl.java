package com.mosinsa.product.command.application;

import com.mosinsa.category.Category;
import com.mosinsa.category.CategoryService;
import com.mosinsa.common.ex.ProductError;
import com.mosinsa.common.ex.ProductException;
import com.mosinsa.product.command.domain.InvalidStockException;
import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.command.domain.ProductId;
import com.mosinsa.product.command.domain.StockStatus;
import com.mosinsa.product.infra.redis.StockOperand;
import com.mosinsa.product.infra.repository.ProductRepository;
import com.mosinsa.product.ui.request.CancelOrderProductRequest;
import com.mosinsa.product.ui.request.CreateProductRequest;
import com.mosinsa.product.ui.request.OrderProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final StockPort stockPort;

	@Override
    @Transactional
    public ProductId createProduct(CreateProductRequest request) {
        Category category = categoryService.getCategory(request.category());

        Product product = productRepository.save(
                Product.create(request.name(),
                        request.price(),
                        category,
                        request.stock()));
        stockPort.setStock(product.getId().getId(), request.stock());
        return product.getId();
    }

	@Override
    @Transactional
    public void orderProduct(String customerId, String orderId, List<OrderProductRequest> orderProducts) {
        log.info("order: {}", orderProducts);

        List<Product> products = getProducts(orderProducts);

        if (!validateStockStatus(products)) {
            throw new AlreadySoldOutException();
        }

        List<StockOperand> stockOperands = getStockOperands(orderProducts);
        StockResult stockResult = stockPort.tryDecrease(customerId, orderId, stockOperands);

        if (StockResult.FAIL.equals(stockResult)) {
            throw new InvalidStockException();
        }
        checkSoldOut(products);
    }

    private void checkSoldOut(List<Product> products) {
        products.stream().filter(product -> stockPort.currentStock(product.getId().getId()) == 0)
                .forEach(p -> p.getStock().updateSoldOut());
    }

    private List<StockOperand> getStockOperands(List<OrderProductRequest> orderProducts) {
        return orderProducts.stream().map(op -> new StockOperand(op.productId(), op.quantity())).toList();
    }

    private List<Product> getProducts(List<OrderProductRequest> orderProducts) {
        return orderProducts.stream().map(request ->
                productRepository.findProductDetailById(ProductId.of(request.productId()))
                        .orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT))).toList();
    }

    private boolean validateStockStatus(List<Product> products) {
        return products.stream().allMatch(p -> p.getStock().getStatus().equals(StockStatus.ON));
    }

	@Override
    @Transactional
    public void cancelOrderProduct(String customerId, String orderId, List<CancelOrderProductRequest> requests) {
        log.info("cancelOrder: {}", requests);
        List<Product> products = getProductList(requests);


        List<StockOperand> stockOperands = requests.stream().map(r -> new StockOperand(r.productId(), r.quantity())).toList();
        StockResult stockResult = stockPort.tryIncrease(customerId, orderId, stockOperands);

        if (StockResult.FAIL.equals(stockResult)) {
            throw new InvalidStockException();
        }

        products.forEach(product -> product.getStock().updateAvailable());
    }

    private List<Product> getProductList(List<CancelOrderProductRequest> requests) {
        return requests.stream().filter(request ->
                        stockPort.currentStock(request.productId()) == 0 && request.quantity() > 0)
                .map(req -> productRepository.findProductDetailById(ProductId.of(req.productId()))
                        .orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT)))
                .toList();
    }
}
