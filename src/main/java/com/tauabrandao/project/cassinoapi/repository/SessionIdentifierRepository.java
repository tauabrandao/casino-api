package com.tauabrandao.project.cassinoapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.tauabrandao.project.cassinoapi.model.SessionIdentifier;

public interface SessionIdentifierRepository extends CrudRepository<SessionIdentifier, Long>{

	SessionIdentifier findByGameId(String gameId);
	
}
