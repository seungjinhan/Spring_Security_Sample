package com.deepplin.security.handler.form;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String errorMessage = "Invalid username or password";
		
		if( exception instanceof BadCredentialsException) {
			errorMessage = "Invalid username or password";
		} else if( exception instanceof InsufficientAuthenticationException) {
			errorMessage = "Invalid Secret Key";
		}
		
		setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);
		
		super.onAuthenticationFailure(request, response, exception);
	}
	
}
