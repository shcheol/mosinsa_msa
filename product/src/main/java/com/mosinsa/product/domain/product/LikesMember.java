package com.mosinsa.product.domain.product;

import jakarta.persistence.*;

@Entity
public class LikesMember {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Likes likes;

	private String memberId;
}
