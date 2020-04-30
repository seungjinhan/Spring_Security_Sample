package com.deepplin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deepplin.domain.AccountVo;

public interface UserRepository extends JpaRepository<AccountVo, Long>{
	
	AccountVo findByUsername( String username);

}
