package com.mosinsa.customer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Certification {

	@NotBlank
	@Column(unique = true)
	private String loginId;

	@NotBlank
	private String password;

	protected static Certification create(String loginId, String password){
		Certification certification = new Certification();
		certification.loginId = loginId;
		certification.password = password;
		return certification;
	}
}
