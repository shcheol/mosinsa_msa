package com.mosinsa.product.infra.repository;

import com.mosinsa.product.query.ProductDetailDto;
import com.mosinsa.product.command.domain.Product;
import com.mosinsa.product.command.domain.ProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, ProductId>, CustomProductRepository {


	@Query(value = "SELECT GET_LOCK(:key, :timeout)", nativeQuery = true)
	void getLock(@Param(value = "key") String key, @Param(value = "timeout") int timeout);

	@Query(value = "SELECT RELEASE_LOCK(:key)", nativeQuery = true)
	void unlock(@Param(value = "key") String key);

	@Query(value = "select p from Product p inner join fetch p.stock inner join fetch p.category where p.id = :productId")
	Optional<Product> findProductDetailById(@Param(value = "productId") ProductId productId);

//	@Query(value = "select p from Product p " +
//			"inner join Likes l on p.likes = l.id " +
//			"inner join LikesMember lm on l.id = lm.likes " +
//			"where p.id =:productId and lm.memberId = :memberId")
//	Optional<Product> likesCheck(@Param(value = "productId") ProductId productId, @Param(value = "memberId") String memberId);

	//	@Query(value = "select new com.mosinsa.product.application.dto.ProductDto(p) " +
//			"from Product p " +
//			"where p.likes in (select l.id from Likes l, LikesMember lm where l.id = lm.likes and :likesMember = lm.memberId )")
//	@Query(value = "select p " +
//			"from Product p " +
//			"inner join Likes l on p.likes = l.id " +
//			"inner join LikesMember lm on l.id = lm.likes " +
//			"where lm.memberId=:likesMember")
//	List<ProductDetailDto> findMyLikesProduct(@Param(value = "likesMember") String likesMember);


}

