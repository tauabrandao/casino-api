package com.tauabrandao.project.cassinoapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.tauabrandao.project.cassinoapi.model.Player;

public interface PlayerRepository extends CrudRepository<Player, Long>{

	boolean existsByEmail(String email);
	
	boolean existsById(Long id);
	
	Player findByEmail(String email);
	
}
