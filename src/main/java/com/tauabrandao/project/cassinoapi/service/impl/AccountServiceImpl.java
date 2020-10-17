package com.tauabrandao.project.cassinoapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tauabrandao.project.cassinoapi.model.Account;
import com.tauabrandao.project.cassinoapi.repository.AccountRepository;
import com.tauabrandao.project.cassinoapi.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository repository;

	@Override
	public void save(Account account) {
		repository.save(account);
	}

}
