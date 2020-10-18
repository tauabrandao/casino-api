package com.tauabrandao.project.cassinoapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tauabrandao.project.cassinoapi.model.Account;
import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.repository.AccountRepository;
import com.tauabrandao.project.cassinoapi.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository repository;

	@Override
	public Account save(Account account) {
		return repository.save(account);
	}

	@Override
	public Account findByPlayer(Player player) {
		return repository.findByPlayer(player);
	}
	
	

}
