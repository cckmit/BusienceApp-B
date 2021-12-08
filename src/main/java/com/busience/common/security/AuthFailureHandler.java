package com.busience.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class AuthFailureHandler implements AuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String errorMessage = "";
		if(exception instanceof AuthenticationServiceException) {
			errorMessage = "시스템에 오류가 발생했습니다.";
		} else if(exception instanceof BadCredentialsException) {
			errorMessage = "아이디나 비밀번호가 맞지 않습니다.\r\n다시 확인해주세요.";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			errorMessage = "아이디나 비밀번호가 맞지 않습니다.\r\n다시 확인해주세요.";
		} else if (exception instanceof DisabledException) {
			errorMessage = "계정이 비활성화 되었습니다.\r\n관리자에게 문의하세요.";
		} else if (exception instanceof CredentialsExpiredException) {
			errorMessage = "비밀번호 유효기간이 만료되었습니다.\r\n관리자에게 문의하세요.";
		} else {
			errorMessage = "계정을 찾을 수 없습니다.";
		}
		System.out.println(errorMessage);
		request.setAttribute("errorMessage", errorMessage);
		
		request.getRequestDispatcher("/?error=true").forward(request, response);
			
	}	
}
