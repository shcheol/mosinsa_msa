package com.mosinsa.order.infra.feignclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mosinsa.order.infra.feignclient.coupon.SimpleCouponResponse;
import feign.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;


@Getter
@Slf4j
public class ResponseResult<T> {

	private static final ResponseResult<?> EMPTY = new ResponseResult<>(0, null, "empty");
	private final int status;
	private final String message;
	private final T data;

	public T get() {
		return data;
	}

	private ResponseResult(int status, T data, String message) {
		this.status = status;
		if (isSuccess()){
			this.data = data;
		}else {
			this.data = null;
		}
		this.message = message;
	}

	public static  <T> ResponseResult<T> execute(Supplier<T> supplier){
		log.info("execute");
		try {
			T t = supplier.get();
			return new ResponseResult<>(200, t, "");
		}catch (Exception e){
			return ResponseResult.empty();
		}


	}

	public static <T> ResponseResult<T> success(T data){
		return new ResponseResult<>(200, data, "");
	}

	public ResponseResult<T> onSuccess(Supplier<T> supplier){
		if (this.isSuccess()){
			supplier.get();
		}
		return this;
	}

	public ResponseResult<T> onFailure(){
		if (this.isSuccess()){

		}
		return this;
	}

	public boolean isSuccess() {
		return 200 <= status && status < 300;
	}

	public boolean isFailure() {
		return !isSuccess();
	}

	@SuppressWarnings("unchecked")
	public static <T> ResponseResult<T> empty() {
		return (ResponseResult<T>) EMPTY;
	}


	@SuppressWarnings("unchecked")
	public static <T> ResponseResult<T> of(Response response) {
		try {
			InputStream inputStream = response.body().asInputStream();
			byte[] bytes = inputStream.readAllBytes();
			ObjectMapper om = new ObjectMapper().registerModule(new JavaTimeModule());
			Object couponResponse = om.readValue(bytes, Object.class);

			T couponResponse1 = om.convertValue(couponResponse, new TypeReference<T>() {
			});

			return new ResponseResult<>(response.status(), couponResponse1, response.reason());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
		if (data != null) {
			return data;
		} else {
			throw exceptionSupplier.get();
		}
	}

	public T orElseDefault(T other) {
		return data != null ? data : other;
	}


	public static void main(String[] args) {
		SimpleCouponResponse empty = SimpleCouponResponse.empty();
		ResponseResult<SimpleCouponResponse> result = new ResponseResult<>(200, empty, null);
		System.out.println(result.getData());
		System.out.println(result.getData().getClass());
		ResponseResult<SimpleCouponResponse> empty2 = ResponseResult.empty();
		System.out.println("empty2 = " + empty2);

		SimpleCouponResponse simpleCouponResponse = empty2.orElseThrow(() -> new IllegalArgumentException());
		Optional<Object> empty1 = Optional.of(new Object[10]);

		Object o = empty1.orElseThrow(IllegalArgumentException::new);


	}
}
