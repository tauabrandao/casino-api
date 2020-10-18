package com.tauabrandao.project.cassinoapi.service;

import java.util.Optional;

import com.tauabrandao.project.cassinoapi.dto.FullPlayerDataDTO;
import com.tauabrandao.project.cassinoapi.model.Player;

public interface PlayerService {
	
	Player save(Player player);
	
	Optional<Player> findById(Long playerId);
	
	void validatePlayerEmail(String email);
	
	boolean existsById(Long id);
	
	FullPlayerDataDTO getFullPlayerDataByPlayerId(Long id);
}
