package com.deepplin.security.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import com.deepplin.domain.AccountDto;
import com.deepplin.security.token.AjaxAuthenticationToken;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

	private ObjectMapper mapper = new ObjectMapper();
	
	public AjaxLoginProcessingFilter() {
		super( new AntPathRequestMatcher("/api/login"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		if( !isAjax( request)) {
			throw new IllegalStateException("Authentication is not support");
		}
		
		AccountDto accountDto = this.mapper.readValue( request.getReader(), AccountDto.class);
		
		if( StringUtils.isEmpty( accountDto.getUsername()) || StringUtils.isEmpty( accountDto.getPassword())) {
			
			throw new IllegalArgumentException("username or password is empty");
		}
			
		AjaxAuthenticationToken token = new AjaxAuthenticationToken( accountDto.getUsername(), accountDto.getPassword());
		
		return getAuthenticationManager().authenticate(token);
	}

	private boolean isAjax(HttpServletRequest request) {
		
		if("XMLHttpRequest".equals( request.getHeader("X-Requested-With"))) {
			return true;
		}
		
		return false;
	}

}
