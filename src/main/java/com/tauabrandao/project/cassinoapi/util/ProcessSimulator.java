package com.tauabrandao.project.cassinoapi.util;

import java.security.SecureRandom;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tauabrandao.project.cassinoapi.model.Account;
import com.tauabrandao.project.cassinoapi.model.Cassino;
import com.tauabrandao.project.cassinoapi.model.Game;
import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.model.SessionIdentifier;
import com.tauabrandao.project.cassinoapi.service.CassinoService;

@Component
public class ProcessSimulator {

	@Autowired
	private CassinoService cassinoService;

	public SessionIdentifier generateIdentifier(String gameId) {
		return SessionIdentifier.builder().gameId(gameId + "_U").sessionId(Long.toString(generateId()) + "_P").build();
	}

	public Game generateGame(Optional<Player> player, Cassino cassino) {
		return Game.builder().id(generateId()).active(false).player(player.get()).cassino(cassino).build();
	}

	public Account generateAccount(Player player) {
		return Account.builder().id(generateId()).active(true).balance(1000.).currency("USD").player(player).build();
	}

	public Cassino generateCassino() {
		String cassinoName = "Simulated Cassino Name";
		Optional<Cassino> cassino = cassinoService.findByName(cassinoName);
		return cassino.isPresent() ? cassino.get() : Cassino.builder().name(cassinoName).build();
	}

	public boolean roundWins() {
		SecureRandom r = new SecureRandom();
		int i = r.nextInt(100);
		return i > 50;
	}
	
	private long generateId() {
		return System.currentTimeMillis();
	}

}
