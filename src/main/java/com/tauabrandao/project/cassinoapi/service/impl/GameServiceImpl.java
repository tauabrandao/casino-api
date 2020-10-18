package com.tauabrandao.project.cassinoapi.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tauabrandao.project.cassinoapi.model.Game;
import com.tauabrandao.project.cassinoapi.repository.GameRepository;
import com.tauabrandao.project.cassinoapi.service.GameService;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	private GameRepository repository;

	@Override
	public Game save(Game game) {
		return repository.save(game);
	}

	@Override
	public Optional<Game> findById(Long id) {
		return repository.findById(id);
		
	}
	
	

}
