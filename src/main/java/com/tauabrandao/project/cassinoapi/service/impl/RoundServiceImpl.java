package com.tauabrandao.project.cassinoapi.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tauabrandao.project.cassinoapi.model.Round;
import com.tauabrandao.project.cassinoapi.repository.RoundRepository;
import com.tauabrandao.project.cassinoapi.service.RoundService;

@Service
public class RoundServiceImpl implements RoundService {

	@Autowired
	private RoundRepository repository;

	@Override
	public Round save(Round round) {
		return repository.save(round);
	}

	@Override
	public Optional<Round> findById(Long id) {
		return repository.findById(id);
	}

	

}
