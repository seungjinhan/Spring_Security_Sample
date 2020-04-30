package com.deepplin.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deepplin.domain.AccountVo;
import com.deepplin.repository.UserRepository;
import com.deepplin.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	@Override
	public void createUser(AccountVo account) {
		this.userRepository.save( account);
	}

}
