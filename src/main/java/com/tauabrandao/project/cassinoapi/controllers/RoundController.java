package com.tauabrandao.project.cassinoapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tauabrandao.project.cassinoapi.model.Round;
import com.tauabrandao.project.cassinoapi.service.RoundService;
import com.tauabrandao.project.cassinoapi.util.ProcessSimulator;

@RestController
public class RoundController {

	@Autowired
	private ProcessSimulator processSimulator;
	
	@Autowired
	private RoundService roundService;
	
	@GetMapping("/round/start")
	public ResponseEntity<Object> startRound(){
		
		try {
			Round round = roundService.save(new Round());
			return new ResponseEntity<Object>(round, HttpStatus.CREATED);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
}
