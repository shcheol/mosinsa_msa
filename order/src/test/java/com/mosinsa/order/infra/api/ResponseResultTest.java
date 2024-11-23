package com.mosinsa.order.infra.api;

import com.mosinsa.order.infra.api.ExternalServerException;
import com.mosinsa.order.infra.api.ResponseResult;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ResponseResultTest {


	@Test
	void execute() {
		ResponseResult<Object> execute = ResponseResult.executeForResponseEntity(() -> ResponseEntity.ok().body("test"));
		assertThat(execute.getStatus().value()).isEqualTo(200);
		assertThat(execute.get()).isEqualTo("test");
		assertThat(execute.getMessage()).isEmpty();
	}

	@Test
	void executeFail() {
		ResponseResult<Object> executeFail = ResponseResult
				.execute(() -> {
					throw new ExternalServerException(HttpStatusCode.valueOf(400), "error message");
				});

		assertThat(executeFail.get()).isNull();
		assertThat(executeFail.getStatus().value()).isEqualTo(400);

		assertThat(executeFail.getMessage()).isEqualTo("error message");
	}

	@Test
	void orElse() {
		ResponseResult<Object> execute = ResponseResult.execute(() -> "test");
		assertThat(execute.orElse("other")).isEqualTo("test");

		ResponseResult<Object> execute2 = ResponseResult.execute(() -> null);
		assertThat(execute2.orElse("other")).isEqualTo("other");

		ResponseResult<Object> executeFail = ResponseResult
				.execute(() -> {
					throw new ExternalServerException(HttpStatusCode.valueOf(400), "");
				});
		assertThat(executeFail.orElse("other")).isEqualTo("other");
	}

	@Test
	void orElseThrow() {
		ResponseResult<Object> execute = ResponseResult.execute(() -> "test");
		assertThat(execute.orElseThrow()).isEqualTo("test");
		ResponseResult<Object> executeFail = ResponseResult
				.execute(() -> {
					throw new ExternalServerException(HttpStatusCode.valueOf(400), "");
				});
		assertThrows(ExternalServerException.class, executeFail::orElseThrow);
	}

	@Test
	void orElseThrowExceptionSupplier() {
		ResponseResult<Object> execute = ResponseResult.execute(() -> "test");
		assertThat(execute.orElseThrow(IllegalArgumentException::new))
				.isEqualTo("test");
		ResponseResult<Object> executeFail = ResponseResult
				.execute(() -> {
					throw new ExternalServerException(HttpStatusCode.valueOf(400), "");
				});
		assertThrows(IllegalArgumentException.class, () -> executeFail.orElseThrow(IllegalArgumentException::new));
	}


	@Test
	void onSuccessWithSuccessResult() {
		State state = new State();
		assertThat(state.getStatus()).isFalse();
		ResponseResult<Object> execute = ResponseResult.execute(() -> "test");
		execute.onSuccess(state::execute);
		assertThat(state.getStatus()).isTrue();
	}

	@Test
	void onSuccessWithFailureResult() {
		State state = new State();
		assertThat(state.getStatus()).isFalse();
		ResponseResult<Object> executeFail = ResponseResult
				.execute(() -> {
					throw new ExternalServerException(HttpStatusCode.valueOf(400), "");
				});
		executeFail.onSuccess(state::execute);
		assertThat(state.getStatus()).isFalse();
	}

	@Test
	void onFailureWithSuccessResult() {
		State state = new State();
		assertThat(state.getStatus()).isFalse();
		ResponseResult<Object> execute = ResponseResult.execute(() -> "test");
		execute.onFailure(state::execute);
		assertThat(state.getStatus()).isFalse();
	}

	static class State {
		private boolean status;

		private void execute() {
			this.status = true;
		}

		private boolean getStatus() {
			return status;
		}
	}


}