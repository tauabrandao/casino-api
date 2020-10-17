package com.tauabrandao.project.cassinoapi.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tauabrandao.project.cassinoapi.exception.BusinessRuleException;
import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.repository.PlayerRepository;
import com.tauabrandao.project.cassinoapi.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	private final String PLAYER_EXISTS_EXCEPTION_MESSAGE = "There is already a player registered with this email";

	@Autowired
	PlayerRepository repository;

	@Override
	public Player save(Player player) {
		return repository.save(player);
		
	}

	@Override
	public Optional<Player> findById(Long playerId) {
		return repository.findById(playerId);
	}

	@Override
	public void validatePlayerEmail(String email) {
		if(repository.existsByEmail(email)) {
			throw new BusinessRuleException(PLAYER_EXISTS_EXCEPTION_MESSAGE);
		}
	
	}

	

}
