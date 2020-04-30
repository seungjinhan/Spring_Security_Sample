package com.deepplin.controller.user;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.deepplin.domain.AccountDto;
import com.deepplin.domain.AccountVo;
import com.deepplin.service.UserService;

@Controller
public class UserController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value="/mypage")
	public String home() throws Exception {
		return "user/mypage";
	}
	
	@GetMapping(value = "/users")
	public String createUser( ) {
		return "user/login/register";
	}
	
	@PostMapping(value = "/users")
	public String createUser( AccountDto account) {
		
		ModelMapper mapper = new ModelMapper();
		AccountVo accountVo = mapper.map( account, AccountVo.class);
		accountVo.setPassword( this.passwordEncoder.encode( account.getPassword()));
		
		this.userService.createUser(accountVo);
		
		return "redirect:/";
	}
}
