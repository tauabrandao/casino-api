package com.tauabrandao.project.cassinoapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.tauabrandao.project.cassinoapi.model.Account;
import com.tauabrandao.project.cassinoapi.model.Player;

public interface AccountRepository extends CrudRepository<Account, Long>{

	Account findByPlayer(Player player);
	
}
