package com.deepplin.security.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.deepplin.domain.AccountVo;

public class AccountContext extends User {

	private AccountVo account;

	public AccountContext( AccountVo account, Collection<? extends GrantedAuthority> authorities) {
	
		super( account.getUsername(), account.getPassword(), authorities);
		this.account = account;
	}
	

	public AccountVo getAccount() {
		return account;
	}
	
}
