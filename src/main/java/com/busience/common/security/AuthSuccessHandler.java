package com.busience.common.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.busience.common.service.LogApiDataService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	LogApiDataService logApiDataService;
	
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        log.info("로그인");
        
        logApiDataService.LogDataInsert("접속", authentication.getName());
        
        response.sendRedirect("/main");
    }
}