package com.deepplin.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AccountVo {

	@Id
	@GeneratedValue
	private Long id;
	private String username;
	private String password;
	private String email;
	private String age;
	private String role;
	
}
