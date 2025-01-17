package com.deepplin.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.deepplin.domain.AccountVo;
import com.deepplin.repository.UserRepository;

@Service("userDetailService")
public class CustomUserDetails implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		AccountVo account = this.userRepository.findByUsername(username);
		
		if( account == null) {
			throw new UsernameNotFoundException(username);
		}
		
		List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
		roles.add( new SimpleGrantedAuthority( account.getRole()));
		
		AccountContext context = new AccountContext(account, roles);
		
		return context;
	}
}
