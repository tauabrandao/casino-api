package com.tauabrandao.project.cassinoapi.util;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tauabrandao.project.cassinoapi.model.Account;
import com.tauabrandao.project.cassinoapi.model.Cassino;
import com.tauabrandao.project.cassinoapi.model.Game;
import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.model.Session;
import com.tauabrandao.project.cassinoapi.model.SessionIdentifier;
import com.tauabrandao.project.cassinoapi.service.SessionIdentifierService;

@Component
public class ProcessSimulator {
	
	@Autowired
	SessionIdentifierService sessionIdentifierService;

	public SessionIdentifier generateIdentifier() {
		SessionIdentifier sessionIdentifier = SessionIdentifier.builder().sessionId(generateSession().getId()).build();
		sessionIdentifierService.save(sessionIdentifier);
		return sessionIdentifier;
	}

//	public Game generateGame() {
//		Player player = Player.builder().id(playerId).build();
//		return Game.builder().id(randomPositiveLong()).active(false).identifier(generateIdentifier()).player(player).build();
//	}
	
	public Account generateAccount() {
		return Account.builder().id(generateId()).active(true).balance(1000.).build();
	}

	private Cassino generateCassino() {
		return Cassino.builder().id(generateId()).name("Cassino name simulated").build();
	}

	private Session generateSession() {
		return Session.builder().id(generateId()).build();
	}

	private long generateId() {
		return System.currentTimeMillis();
	}

}
