package com.deepplin.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	private RequestCache cache = new HttpSessionRequestCache();
	
	private RedirectStrategy strategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		setDefaultTargetUrl("/");
		
		SavedRequest savedRequest = cache.getRequest(request, response);
		if( savedRequest != null) {
			String targetUrl = savedRequest.getRedirectUrl();
			strategy.sendRedirect(request, response, targetUrl);
		}
		else {
			strategy.sendRedirect(request, response, getDefaultTargetUrl());
		}
	}
}