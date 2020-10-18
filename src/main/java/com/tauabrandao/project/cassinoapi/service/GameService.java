package com.tauabrandao.project.cassinoapi.service;

import java.util.Optional;

import com.tauabrandao.project.cassinoapi.model.Game;

public interface GameService {
	
	Game save(Game game);
	
	Optional<Game> findById(Long id);

}
