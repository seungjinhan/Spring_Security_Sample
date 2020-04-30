package com.deepplin.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.deepplin.security.common.FormWebAuthenticationDetailsSource;
import com.deepplin.security.handler.CustomAccessDeniedHandler;
import com.deepplin.security.handler.CustomAuthenticationFailureHandler;
import com.deepplin.security.handler.CustomAuthenticationSuccessHandler;
import com.deepplin.security.provider.FormAuthenticationProvider;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private FormWebAuthenticationDetailsSource authenticationDetailsSource;
	
	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;
	
	@Autowired
	private CustomAuthenticationFailureHandler failureHandler;
	
	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		CustomAccessDeniedHandler handler = new CustomAccessDeniedHandler();
		handler.setErrorPage("/denied");
		
		return handler; 
	}
	
	@Bean
	public PasswordEncoder passwordEncoder( ) {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		return new FormAuthenticationProvider();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider( authenticationProvider());
		//auth.userDetailsService(userDetailService);
		
//		String pw = this.passwordEncoder().encode("1234");
//		
//		auth
//		
//				.inMemoryAuthentication()
//				.withUser("user").password( pw).roles("USER")
//				.and()
//				.withUser("manager").password( pw).roles("MANAGER" , "USER")
//				.and()
//				.withUser("admin").password( pw).roles("ADMIN", "USER" , "MANAGER");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
				.authorizeRequests()
				.antMatchers("/", "/users" , "user/login/**" , "/login*").permitAll()
				.antMatchers("/mypage").hasRole("USER")
				.antMatchers("/messages").hasRole("MANAGER")
				.antMatchers("/config").hasRole("ADMIN")
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				.loginProcessingUrl("/login_proc")
				.authenticationDetailsSource(this.authenticationDetailsSource)
				.defaultSuccessUrl("/")
				.successHandler( this.successHandler)
				.failureHandler( this.failureHandler)
				.permitAll()
			.and()
				.exceptionHandling()
				.accessDeniedHandler( this.accessDeniedHandler());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web
				.ignoring()
				.requestMatchers( PathRequest.toStaticResources().atCommonLocations());
	}

	
}