package com.busience.common.security;

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
	AuthFailureHandler authFailureHandler;

	@Autowired
	BusienceUsersService busienceUsersService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		//System.out.println("=====================");
		//System.out.println(passwordEncoder().encode("123"));
		//System.out.println(passwordEncoder().encode("1234"));
		
		http.authorizeRequests()
			.antMatchers("/css/**","/js/**","/fonts/**","/images/**").permitAll();
		
		http.authorizeRequests()
			.antMatchers("/*").hasRole("ADMIN");
		
		http.formLogin()
			.loginPage("/")
			//.failureHandler(authFailureHandler)
			.defaultSuccessUrl("/main")
			.permitAll();
			
		
		
		http.logout()
			.logoutUrl("/logout").invalidateHttpSession(true)
			.permitAll();
		
		http.userDetailsService(busienceUsersService);
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(busienceUsersService)
			.passwordEncoder(passwordEncoder());
		
	}
}