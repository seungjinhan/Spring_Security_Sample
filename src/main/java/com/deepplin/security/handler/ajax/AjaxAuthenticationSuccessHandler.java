package com.deepplin.security.handler.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.deepplin.domain.AccountVo;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private ObjectMapper mapper = new ObjectMapper();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		AccountVo account = (AccountVo) authentication.getPrincipal();
		response.setStatus( HttpStatus.OK.value());
		response.setContentType( MediaType.APPLICATION_JSON_VALUE);
		
		mapper.writeValue( response.getWriter(), account);
		
	}

}
