package com.tauabrandao.project.cassinoapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tauabrandao.project.cassinoapi.model.Account;
import com.tauabrandao.project.cassinoapi.model.Bet;
import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.repository.AccountRepository;
import com.tauabrandao.project.cassinoapi.repository.BetRepository;
import com.tauabrandao.project.cassinoapi.service.AccountService;
import com.tauabrandao.project.cassinoapi.service.BetService;

@Service
public class BetAccountServiceImpl implements BetService {

	@Autowired
	private BetRepository repository;

	@Override
	public Bet save(Bet bet) {
		return repository.save(bet);
	}

	

}
