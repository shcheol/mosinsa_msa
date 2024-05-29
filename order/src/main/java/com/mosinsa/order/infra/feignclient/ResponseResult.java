package com.mosinsa.order.infra.feignclient;

import feign.FeignException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;


@Getter
@Slf4j
public class ResponseResult<T> {

    private static final ResponseResult<?> EMPTY = new ResponseResult<>(0, null, "empty");
    private final int status;
    private final String message;
    private final T data;

    private ResponseResult(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ResponseResult<T> execute(Supplier<T> supplier) {
        log.info("execute");
        try {
            T t = supplier.get();
			log.info("execute {}", t);
            return new ResponseResult<>(200, t, "");
        } catch (FeignException ex) {
			return new ResponseResult<>(ex.status(), null, ex.getLocalizedMessage());
        } catch (Exception e) {
			return new ResponseResult<>(500, null, e.getMessage());
        }
    }

	public static <T> ResponseResult<T> execute(Runnable runnable) {
		log.info("execute");
		try {
			runnable.run();
			return new ResponseResult<>(200, null, "");
		} catch (FeignException ex) {
			return new ResponseResult<>(ex.status(), null, ex.getLocalizedMessage());
		} catch (Exception e) {
			return new ResponseResult<>(500, null, e.getMessage());
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
        return 200 <= status && status < 300;
    }

    private boolean isFailure() {
        return !isSuccess();
    }

    @SuppressWarnings("unchecked")
    public static <T> ResponseResult<T> empty() {
        return (ResponseResult<T>) EMPTY;
    }

    public T get() {
        return data;
    }

    public T orElseThrow() {
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
