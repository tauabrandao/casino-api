package com.tauabrandao.project.cassinoapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tauabrandao.project.cassinoapi.model.Round;
import com.tauabrandao.project.cassinoapi.service.RoundService;
import com.tauabrandao.project.cassinoapi.util.ProcessSimulator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Round Controller")
public class RoundController {

	@Autowired
	private ProcessSimulator processSimulator;
	
	@Autowired
	private RoundService roundService;
	
	@GetMapping("/round/start")
	@ApiOperation(value = "Start a new round")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<Object> startRound(){
		
		try {
			Round round = roundService.save(new Round());
			return new ResponseEntity<Object>(round, HttpStatus.CREATED);
		}catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
}
