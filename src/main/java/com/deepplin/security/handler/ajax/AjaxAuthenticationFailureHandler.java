package com.deepplin.security.handler.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String errorMessage = "Invalid Username or Password";
		
		response.setStatus( HttpStatus.UNAUTHORIZED.value());
		response.setContentType( MediaType.APPLICATION_JSON_VALUE);
		
		if( exception instanceof BadCredentialsException) {
			errorMessage = "Invalid username or password";
		} else if( exception instanceof InsufficientAuthenticationException) {
			errorMessage = "Invalid Secret Key";
		}
		
		mapper.writeValue( response.getWriter(), errorMessage);
	}

}
