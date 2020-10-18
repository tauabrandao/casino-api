package com.tauabrandao.project.cassinoapi.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tauabrandao.project.cassinoapi.model.Cassino;
import com.tauabrandao.project.cassinoapi.repository.CassinoRepository;
import com.tauabrandao.project.cassinoapi.service.CassinoService;

@Service
public class CassinoServiceImpl implements CassinoService {

	@Autowired
	private CassinoRepository repository;

	@Override
	public Cassino save(Cassino cassino) {
		return repository.save(cassino);
	}

	@Override
	public Optional<Cassino> findByName(String name) {
		return repository.findByName(name);
	}
	
	

}
