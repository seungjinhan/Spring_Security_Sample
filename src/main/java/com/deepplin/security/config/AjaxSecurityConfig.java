package com.deepplin.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.deepplin.security.common.ajax.AjaxLoginAuthenticationEntryPoint;
import com.deepplin.security.filter.AjaxLoginProcessingFilter;
import com.deepplin.security.handler.ajax.AjaxAccessDeniedHandler;
import com.deepplin.security.handler.ajax.AjaxAuthenticationFailureHandler;
import com.deepplin.security.handler.ajax.AjaxAuthenticationSuccessHandler;
import com.deepplin.security.provider.AjaxAuthenticationProvider;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
@Order(0)
public class AjaxSecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Bean
	public AjaxAuthenticationProvider ajaxAuthenticationProvider() {
		return new AjaxAuthenticationProvider();
	}

	@Bean
	public AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
		return new AjaxAuthenticationSuccessHandler();
	}
	
	@Bean
	public AuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
		return new AjaxAuthenticationFailureHandler();
	}
	
	@Bean
	public AccessDeniedHandler ajaxAccessDeniedHandler() {
		return new AjaxAccessDeniedHandler();
	}
	
	@Bean
	public AjaxLoginProcessingFilter ajaxLoginProcessingFilter() throws Exception {
		
		AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter();
		filter.setAuthenticationManager( this.authenticationManagerBean());
		filter.setAuthenticationSuccessHandler( this.ajaxAuthenticationSuccessHandler());
		filter.setAuthenticationFailureHandler( this.ajaxAuthenticationFailureHandler());
		
		return filter;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider( this.ajaxAuthenticationProvider());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
				.antMatcher("/api/**")
				.authorizeRequests()
				.antMatchers("/api/messages").hasRole("MANAGER")
				.anyRequest().authenticated()
			.and()
				.addFilterBefore( this.ajaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class);

		http
				.exceptionHandling()
				.authenticationEntryPoint( new AjaxLoginAuthenticationEntryPoint())
				.accessDeniedHandler( ajaxAccessDeniedHandler());
		
		http.csrf().disable();
	}


	
		
}