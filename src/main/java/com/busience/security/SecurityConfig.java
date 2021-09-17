package com.busience.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	public AuthFailureHandler authFailureHandler;
	
	@Autowired
	DataSource datasource;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		
		http.authorizeRequests()
			.antMatchers("/css/**","/js/**","/fonts/**","/images/**").permitAll()
			.anyRequest().authenticated();
		
		http.formLogin()
			.loginPage("/")
			.failureHandler(authFailureHandler)
			.defaultSuccessUrl("/main")
			.usernameParameter("username")
			.passwordParameter("password")
			.permitAll();
			
		
		http.logout()
			.logoutUrl("/logout").permitAll();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		//System.out.println(passwordEncoder().encode("123"));
		
		String query1 = "select USER_CODE as username, USER_PASSWORD as upw, true enabled \r\n"
				+ "from USER_INFO_TBL where USER_CODE = ?";
		
		String query2 = "select USER_CODE as username, 'ADMIN' authority \r\n"
				+ "from USER_INFO_TBL where USER_CODE = ?";

		auth.jdbcAuthentication()
			.dataSource(datasource)
			.usersByUsernameQuery(query1)
			.rolePrefix("ROLE_")
			.authoritiesByUsernameQuery(query2)
			.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}