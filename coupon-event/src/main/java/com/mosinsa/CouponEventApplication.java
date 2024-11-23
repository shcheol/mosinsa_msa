package com.mosinsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "com.mosinsa")
public class CouponEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponEventApplication.class, args);
	}

}
