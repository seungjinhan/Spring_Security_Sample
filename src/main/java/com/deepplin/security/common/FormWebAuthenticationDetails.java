package com.deepplin.security.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class FormWebAuthenticationDetails extends WebAuthenticationDetails {

	
	private String secretKey;
	
	public FormWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		this.secretKey = request.getParameter("secretKey");
	}

	public String getSecretKey() {
		return secretKey;
	}
	
}
