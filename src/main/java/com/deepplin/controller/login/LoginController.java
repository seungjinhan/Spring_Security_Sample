package com.deepplin.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.deepplin.domain.AccountVo;

@Controller
public class LoginController {

	@GetMapping( value = {"/login", "/api/login"})
	public String login( 
			@RequestParam( value = "error", required = false) String error,
			@RequestParam( value = "exception", required = false) String exception,
			Model model
			
			) {
		
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		
		return "login";
	}
	
	@GetMapping("/logout")
	public String logout( 
			HttpServletRequest request,
			HttpServletResponse response) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if( authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		}
		return "redirect:/login";
	}
	
	
	@GetMapping( value = {"/denied" , "/api/denied"})
	public String accessDenied(
			@RequestParam( value = "exception", required = false) String exception, Model model) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		AccountVo account = (AccountVo) authentication.getPrincipal();
		model.addAttribute("username", account.getUsername());
		model.addAttribute("exception", exception);
		
		return "user/login/denied";
		
	}
	
		
}
