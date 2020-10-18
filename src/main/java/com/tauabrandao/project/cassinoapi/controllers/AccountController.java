package com.tauabrandao.project.cassinoapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tauabrandao.project.cassinoapi.constants.DefaultConstants;
import com.tauabrandao.project.cassinoapi.model.Account;
import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.service.AccountService;
import com.tauabrandao.project.cassinoapi.service.PlayerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Account controller")
public class AccountController {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private DefaultConstants constants;

	@Autowired
	private AccountService accountService;

	@GetMapping("/balance/{playerId}")
	@ApiOperation(value = "Get currency and balance account from player")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<Object> getCurrencyAndBalanceFromPlayer(@PathVariable("playerId") String playerId) {

		ResponseEntity<Object> responseError = ResponseEntity.badRequest()
				.body(constants.PLAYER_DOES_NOT_EXIST_EXCEPTION_MESSAGE);

		if (!playerService.existsById(Long.parseLong(playerId))) {
			return responseError;

		}

		Account account = accountService.findByPlayer(Player.builder().id(Long.parseLong(playerId)).build());

		if (account != null) {
			return new ResponseEntity<Object>(account, HttpStatus.OK);
		} else {
			return responseError;
		}

	}

}
