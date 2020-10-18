package com.tauabrandao.project.cassinoapi.service;

import com.tauabrandao.project.cassinoapi.model.Account;
import com.tauabrandao.project.cassinoapi.model.Player;

public interface AccountService {
	
	Account save(Account account);
	
	Account findByPlayer(Player player);

}
