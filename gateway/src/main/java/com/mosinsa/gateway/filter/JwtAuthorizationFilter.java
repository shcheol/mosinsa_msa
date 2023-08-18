package com.mosinsa.gateway.filter;

import com.mosinsa.gateway.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		// 1. 토큰이 필요하지 않은 API URL에 대해서 배열로 구성합니다.
		List<String> list = Arrays.asList(
				"/api/v1/user/login",
				"/api/v1/test/generateToken"
//                "api/v1/code/codeList"
		);

		if (list.contains(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}

		// [STEP1] Client에서 API를 요청할때 Header를 확인합니다.
		String header = request.getHeader("Authorization");
		logger.debug("[+] header Check: " + header);

		try {
			if (!StringUtils.hasText(header)) {
				throw new IllegalStateException("TOKEN isEmpty");
			}
			String token = JwtUtils.getToken(header);
			if (!JwtUtils.isValid(token)) {
				throw new IllegalStateException("TOKEN invalid");
			}

			String userId = JwtUtils.getCustomerId(token);
			if (!StringUtils.hasText(userId)) {
				throw new IllegalStateException("TOKEN isn't userId");
			}

			logger.debug("[+] userId Check: " + userId);

			filterChain.doFilter(request, response);


		} catch (
				Exception e) {
			// Token 내에 Exception이 발생 하였을 경우 => 클라이언트에 응답값을 반환하고 종료합니다.
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json");
//			PrintWriter printWriter = response.getWriter();
//			JSONObject jsonObject = jsonResponseWrapper(e);
//			printWriter.print(jsonObject);
//			printWriter.flush();
//			printWriter.close();
		}


	}
}
