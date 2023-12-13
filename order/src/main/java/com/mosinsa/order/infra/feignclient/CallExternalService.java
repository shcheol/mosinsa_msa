package com.mosinsa.order.infra.feignclient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CallExternalService {

	private final ProductClient productClient;




}
