package com.tauabrandao.project.cassinoapi.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tauabrandao.project.cassinoapi.dto.FullPlayerDataDTO;
import com.tauabrandao.project.cassinoapi.exception.BusinessRuleException;
import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.repository.PlayerRepository;
import com.tauabrandao.project.cassinoapi.repository.dao.PlayerDAO;
import com.tauabrandao.project.cassinoapi.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {
	
	@Value("${cassinoapi.message.playerExistsExceptionMessage}")
	private String PLAYER_EXISTS_EXCEPTION_MESSAGE;

	@Autowired
	private PlayerRepository repository;
	
	@Autowired
	private PlayerDAO customRepository;

	@Override
	public Player save(Player player) {
		player.setPassword(new BCryptPasswordEncoder().encode(player.getPassword()));
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

	@Override
	public boolean existsById(Long id) {
		return repository.existsById(id);
	}

	@Override
	public FullPlayerDataDTO getFullPlayerDataByPlayerId(Long id) {
		return customRepository.getFullPlayerDataByPlayerID(id);
	}

	
	

}
