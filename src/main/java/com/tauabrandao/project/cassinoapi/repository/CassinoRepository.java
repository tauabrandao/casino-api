package com.tauabrandao.project.cassinoapi.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.tauabrandao.project.cassinoapi.model.Cassino;

public interface CassinoRepository extends CrudRepository<Cassino, Long>{

	Optional<Cassino> findByName(String name);

	
}
