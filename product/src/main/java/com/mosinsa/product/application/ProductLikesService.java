package com.mosinsa.product.application;

import com.mosinsa.product.common.ex.ProductError;
import com.mosinsa.product.common.ex.ProductException;
import com.mosinsa.product.domain.product.LikesMember;
import com.mosinsa.product.domain.product.Product;
import com.mosinsa.product.domain.product.ProductId;
import com.mosinsa.product.infra.repository.LikesMemberRepository;
import com.mosinsa.product.infra.repository.ProductRepository;
import com.mosinsa.product.ui.request.LikesProductRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductLikesService {

	private final ProductRepository productRepository;
	private final LikesMemberRepository likesMemberRepository;


	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void likes(LikesProductRequest request) {
		Product product = productRepository.findProductDetailById(ProductId.of(request.productId()))
				.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT));
		likesMemberRepository.save(LikesMember.create(request.memberId(), product.getLikes()));
		product.likes();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void likesCancel(LikesProductRequest request) {

		Product product = productRepository.findProductDetailById(ProductId.of(request.productId()))
				.orElseThrow(() -> new ProductException(ProductError.NOT_FOUNT_PRODUCT));

		likesMemberRepository.deleteLikesMemberByLikesIdAndMemberId(request.memberId(), product.getLikes());

		product.likesCancel();
	}
}
