package com.tauabrandao.project.cassinoapi.service;

import java.util.Optional;

import com.tauabrandao.project.cassinoapi.model.Round;

public interface RoundService {
	
	Round save(Round round);
	
	Optional<Round> findById(Long id);
	
}
