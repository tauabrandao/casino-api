package com.tauabrandao.project.cassinoapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tauabrandao.project.cassinoapi.model.SessionIdentifier;
import com.tauabrandao.project.cassinoapi.repository.SessionIdentifierRepository;
import com.tauabrandao.project.cassinoapi.service.SessionIdentifierService;

@Service
public class SessionIdentifierServiceImpl implements SessionIdentifierService {

	@Autowired
	private SessionIdentifierRepository repository;

	@Override
	public SessionIdentifier save(SessionIdentifier sessionIdentifier) {
		sessionIdentifier.setSessionId(new BCryptPasswordEncoder().encode(sessionIdentifier.getSessionId()));
		return repository.save(sessionIdentifier);
	}

}
