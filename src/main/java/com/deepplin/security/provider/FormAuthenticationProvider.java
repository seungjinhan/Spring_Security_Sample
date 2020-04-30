package com.deepplin.security.provider;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.deepplin.security.common.FormWebAuthenticationDetails;
import com.deepplin.security.service.AccountContext;

public class FormAuthenticationProvider implements AuthenticationProvider {

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
	
		FormWebAuthenticationDetails details = (FormWebAuthenticationDetails)auth.getDetails();
		String secretKey = details.getSecretKey();
		if( secretKey == null || !"secret".equals(secretKey)) {
			throw new InsufficientAuthenticationException("secretKey");
		}
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( account.getAccount(), null, account.getAuthorities());
		
		return token;
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals( UsernamePasswordAuthenticationToken.class);
	}

}
