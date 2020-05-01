package com.deepplin.security.provider;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.deepplin.security.service.AccountContext;
import com.deepplin.security.token.AjaxAuthenticationToken;

public class AjaxAuthenticationProvider implements AuthenticationProvider {


	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		String username = auth.getName();
		String password = (String) auth.getCredentials();
		
		AccountContext account = (AccountContext)this.userDetailService.loadUserByUsername(username);
		
		if( !this.passwordEncoder.matches( password, account.getPassword())) {
			throw new BadCredentialsException(username);
		}
	
		return new AjaxAuthenticationToken( account.getAccount(), null, account.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals( AjaxAuthenticationToken.class);
	}

}
