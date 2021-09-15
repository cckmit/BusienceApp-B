package com.busience.security;

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
		
		String loginid = request.getParameter("username");
		String errormsg = "";
		if(exception instanceof AuthenticationServiceException) {
			errormsg = "시스템에 오류가 발생했습니다.";
		} else if(exception instanceof BadCredentialsException) {
			errormsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해주세요1";
		} else if (exception instanceof InternalAuthenticationServiceException) {
			errormsg = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해주세요2";
		} else if (exception instanceof DisabledException) {
			errormsg = "계정이 비활성화 되었습니다. 관리자에게 문의하세요";
		} else if (exception instanceof CredentialsExpiredException) {
			errormsg = "비밀번호 유효기간이 만료되었습니다. 관리자에게 문의하세요";
		} else {
			errormsg = "계정을 찾을 수 없습니다.";
		}
		
		request.setAttribute("username", loginid);
		request.setAttribute("error_message", errormsg);
		
		System.out.println(loginid);
		System.out.println(errormsg);
		
		request.getRequestDispatcher("/?error").forward(request, response);
			
	}	
}
