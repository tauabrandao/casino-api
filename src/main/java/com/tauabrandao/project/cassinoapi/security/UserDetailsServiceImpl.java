package com.tauabrandao.project.cassinoapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.model.SessionIdentifier;
import com.tauabrandao.project.cassinoapi.repository.PlayerRepository;
import com.tauabrandao.project.cassinoapi.repository.SessionIdentifierRepository;

@Repository
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private SessionIdentifierRepository repository;

	@Override
	public UserDetails loadUserByUsername(String gameId) throws UsernameNotFoundException {
		SessionIdentifier sessionIdentifier = repository.findByGameId(gameId);

		if (sessionIdentifier == null) {
			throw new UsernameNotFoundException("Session identifier not found");
		}
		return sessionIdentifier;
	}

}
