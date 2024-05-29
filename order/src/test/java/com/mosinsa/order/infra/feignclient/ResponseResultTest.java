package com.mosinsa.order.infra.feignclient;

import feign.FeignException;
import feign.Request;
import feign.Response;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResponseResultTest {

    @Test
    void create(){
        ResponseResult<Object> empty = ResponseResult.empty();
        assertThat(empty.get()).isNull();
        assertThat(empty.getStatus()).isZero();
        assertThat(empty.getMessage()).isEqualTo("empty");
    }

    @Test
    void execute(){
        ResponseResult<Object> execute = ResponseResult.execute(() -> "test");
        assertThat(execute.getStatus()).isEqualTo(200);
        assertThat(execute.get()).isEqualTo("test");
        assertThat(execute.getMessage()).isEmpty();
    }

    @Test
    void executeFeignException(){
        FeignException feignException = FeignException
                .errorStatus("", Response.builder()
                        .request(Request.create(Request.HttpMethod.GET, "", Map.of(), null, Charset.defaultCharset(), null))
                        .status(400).build());

        ResponseResult<Object> executeFail = ResponseResult.execute(() -> {
            throw feignException;
        });
        assertThat(executeFail.getStatus()).isEqualTo(feignException.status());
        assertThat(executeFail.getMessage()).isEqualTo(feignException.getLocalizedMessage());
        assertThat(executeFail.get()).isNull();
    }

    @Test
    void executeFail(){
        ResponseResult<Object> executeFail = ResponseResult.execute(() -> {
            throw new RuntimeException("error message");
        });

        assertThat(executeFail.get()).isNull();
        assertThat(executeFail.getStatus()).isEqualTo(500);

        assertThat(executeFail.getMessage()).isEqualTo("error message");
    }

    @Test
    void orElse(){
        ResponseResult<Object> execute = ResponseResult.execute(() -> "test");
        assertThat(execute.orElse("other")).isEqualTo("test");

        ResponseResult<Object> execute2 = ResponseResult.execute(() -> null);
        assertThat(execute2.orElse("other")).isEqualTo("other");

        ResponseResult<Object> executeFail = ResponseResult.execute(() -> {
            throw new RuntimeException();
        });
        assertThat(executeFail.orElse("other")).isEqualTo("other");
    }

    @Test
    void orElseThrow(){
        ResponseResult<Object> execute = ResponseResult.execute(() -> "test");
        assertThat(execute.orElseThrow()).isEqualTo("test");
        ResponseResult<Object> executeFail = ResponseResult.execute(() -> {
            throw new RuntimeException();
        });
        assertThrows(ExternalServerException.class, executeFail::orElseThrow);
    }

    @Test
    void orElseThrowExceptionSupplier(){
        ResponseResult<Object> execute = ResponseResult.execute(() -> "test");
        assertThat(execute.orElseThrow(IllegalArgumentException::new))
                .isEqualTo("test");
        ResponseResult<Object> executeFail = ResponseResult.execute(() -> {
            throw new RuntimeException();
        });
        assertThrows(IllegalArgumentException.class, () ->executeFail.orElseThrow(IllegalArgumentException::new));
    }


    @Test
    void onSuccessWithSuccessResult(){
        State state = new State();
        assertThat(state.getStatus()).isFalse();
        ResponseResult<Object> execute = ResponseResult.execute(() -> "test");
        execute.onSuccess(state::execute);
        assertThat(state.getStatus()).isTrue();
      }

    @Test
    void onSuccessWithFailureResult(){
        State state = new State();
        assertThat(state.getStatus()).isFalse();
        ResponseResult<Object> executeFail = ResponseResult.execute(() -> {
            throw new RuntimeException();
        });
        executeFail.onSuccess(state::execute);
        assertThat(state.getStatus()).isFalse();
    }

    @Test
    void onFailureWithSuccessResult(){
        State state = new State();
        assertThat(state.getStatus()).isFalse();
        ResponseResult<Object> execute = ResponseResult.execute(() -> "test");
        execute.onFailure(state::execute);
        assertThat(state.getStatus()).isFalse();
    }

    @Test
    void onFailureWithFailureResult(){
        State state = new State();
        assertThat(state.getStatus()).isFalse();
        FeignException feignException = FeignException
                .errorStatus("", Response.builder()
                        .request(Request.create(Request.HttpMethod.GET, "", Map.of(), null, Charset.defaultCharset(), null))
                        .status(300).build());

        ResponseResult<Object> executeFail = ResponseResult.execute(() -> {
            throw feignException;
        });
        executeFail.onFailure(state::execute);
        assertThat(state.getStatus()).isTrue();
    }

    static class State{
        private boolean status;

        private void execute(){
            this.status = true;
        }

        private boolean getStatus(){
            return status;
        }
    }
}