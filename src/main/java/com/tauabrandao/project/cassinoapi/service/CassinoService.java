package com.tauabrandao.project.cassinoapi.service;

import java.util.Optional;

import com.tauabrandao.project.cassinoapi.model.Cassino;

public interface CassinoService {
	
	Cassino save(Cassino cassino);
	
	Optional<Cassino> findByName(String name);

}
