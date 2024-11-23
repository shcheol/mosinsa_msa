package com.mosinsa.order.infra.api;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;


@Getter
@Slf4j
public class ResponseResult<T> {

	private final HttpStatus status;
	private final String message;
	private final T data;

	protected ResponseResult(int status, T data, String message) {
		this.status = HttpStatus.valueOf(status);
		this.data = data;
		this.message = message;
	}

	public static <T> ResponseResult<T> execute(Supplier<T> supplier) {
		log.info("execute supplier");
		try {
			T t = supplier.get();
			log.info("execute {}", t);
			return new ResponseResult<>(200, t, "");
		} catch (ExternalServerException e) {
			log.error("exception {}", e.getMessage());
			return new ResponseResult<>(e.getStatus().value(), null, e.getMessage());
		}
	}

	public static <T> ResponseResult<T> executeForResponseEntity(Supplier<? extends ResponseEntity<T>> supplier) {
		log.info("execute supplier");
		try {
			ResponseEntity<T> responseEntity = supplier.get();
			return new ResponseResult<>(responseEntity.getStatusCode().value(), responseEntity.getBody(), "");
		} catch (ExternalServerException e) {
			log.error("exception {}", e.getMessage());
			return new ResponseResult<>(e.getStatus().value(), null, e.getMessage());
		}
	}

	public ResponseResult<T> onSuccess(Runnable runnable) {
		if (this.isSuccess()) {
			runnable.run();
		}
		return this;
	}

	public ResponseResult<T> onFailure(Runnable runnable) {
		if (this.isFailure()) {
			runnable.run();
		}
		return this;
	}

	private boolean isSuccess() {
		return status.is2xxSuccessful();
	}

	private boolean isFailure() {
		return !isSuccess();
	}

	public T get() {
		return data;
	}

	public T orElseThrow() {
		log.info("status: {}", this.status);
		if (this.isSuccess()) {
			return data;
		}
		throw new ExternalServerException(this.status, this.message);
	}

	public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
		if (this.isSuccess()) {
			return data;
		} else {
			throw exceptionSupplier.get();
		}
	}

	public T orElse(T other) {
		return this.isSuccess() && data != null ? data : other;
	}

}
