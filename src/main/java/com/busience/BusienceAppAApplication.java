package com.busience;

import javax.servlet.http.HttpSessionListener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.busience.common.security.SessionListener;

@SpringBootApplication
public class BusienceAppAApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusienceAppAApplication.class, args);
	}

	@Bean
	public HttpSessionListener httpSessionListener(){
		return new SessionListener();
	}
}
