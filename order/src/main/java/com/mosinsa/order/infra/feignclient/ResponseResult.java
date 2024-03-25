package com.mosinsa.order.infra.feignclient;

import feign.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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
            return new ResponseResult<>(200, t, "");
        } catch (ResponseFailureException ex) {
            Response response = ex.getResponse();
            return new ResponseResult<>(response.status(), null, response.reason());
        } catch (Exception e) {
            return ResponseResult.empty();
        }
    }

    public ResponseResult<T> onSuccess(Supplier<T> supplier) {
        if (this.isSuccess()) {
            return ResponseResult.execute(supplier);
        }
        return this;
    }

    public ResponseResult<T> onFailure(Supplier<T> supplier) {
        if (!this.isSuccess()) {
            return ResponseResult.execute(supplier);
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

    public <X extends Throwable> T orElseThrow() throws X {
        if (this.isSuccess()) {
            return data;
        } else {
            throw new ExternalErrorException(this);
        }
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.isSuccess()) {
            return data;
        } else {
            throw exceptionSupplier.get();
        }
    }

    public T orElse(T other) {
        return data != null ? data : other;
    }

}
