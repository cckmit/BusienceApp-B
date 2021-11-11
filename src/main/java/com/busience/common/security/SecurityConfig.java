package com.busience.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
    @Autowired
    AuthSuccessHandler authSuccessHandler;
	
	@Autowired
	AuthFailureHandler authFailureHandler;

    @Autowired
    CustomLogoutSuccessHandler customLogoutSuccessHandler;
	
	@Autowired
	BusienceUsersService busienceUsersService;
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		
		web.ignoring()
			.antMatchers("/css/**","/js/**","/fonts/**","/images/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests()
			.antMatchers("/", "/bsapp2", "/productAdd").permitAll();
		
		http.authorizeRequests()
			.antMatchers("/*").hasRole("ADMIN");
		
		http.formLogin()
			.loginPage("/")
            .successHandler(authSuccessHandler)
			.failureHandler(authFailureHandler)
			.permitAll();
		
        http.sessionManagement()
	        .maximumSessions(1)
	        .maxSessionsPreventsLogin(false)
	        .expiredUrl("/")
	        .sessionRegistry(sessionRegistry());
			
        http.logout()
			.logoutUrl("/logout")
			.invalidateHttpSession(true)
			.logoutSuccessHandler(customLogoutSuccessHandler)
			.permitAll();
		
		http.userDetailsService(busienceUsersService);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.userDetailsService(busienceUsersService)
			.passwordEncoder(passwordEncoder());
				
	}
}