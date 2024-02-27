package com.mosinsa.product.infra.repository;

import com.mosinsa.product.domain.product.Likes;
import com.mosinsa.product.domain.product.LikesMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesMemberRepository extends JpaRepository<LikesMember, String> {

	@Modifying
	@Query(value = "delete from LikesMember lm where lm.likes = :likes and lm.memberId = :memberId")
	void deleteLikesMemberByLikesIdAndMemberId(@Param("memberId") String memberId, @Param("likes") Likes likes);
}
