package com.tauabrandao.project.cassinoapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tauabrandao.project.cassinoapi.constants.DefaultConstants;
import com.tauabrandao.project.cassinoapi.dto.IdentifierDTO;
import com.tauabrandao.project.cassinoapi.dto.OpenGameDTO;
import com.tauabrandao.project.cassinoapi.dto.PlayGameDTO;
import com.tauabrandao.project.cassinoapi.dto.RoundResultDTO;
import com.tauabrandao.project.cassinoapi.exception.BusinessRuleException;
import com.tauabrandao.project.cassinoapi.model.Account;
import com.tauabrandao.project.cassinoapi.model.Bet;
import com.tauabrandao.project.cassinoapi.model.Cassino;
import com.tauabrandao.project.cassinoapi.model.Game;
import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.model.Round;
import com.tauabrandao.project.cassinoapi.model.SessionIdentifier;
import com.tauabrandao.project.cassinoapi.service.AccountService;
import com.tauabrandao.project.cassinoapi.service.BetService;
import com.tauabrandao.project.cassinoapi.service.CassinoService;
import com.tauabrandao.project.cassinoapi.service.GameService;
import com.tauabrandao.project.cassinoapi.service.PlayerService;
import com.tauabrandao.project.cassinoapi.service.RoundService;
import com.tauabrandao.project.cassinoapi.service.SessionIdentifierService;
import com.tauabrandao.project.cassinoapi.util.ProcessSimulator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Game Controller")
public class GameController {

	@Autowired
	private PlayerService playerService;

	@Autowired
	private ProcessSimulator processSimulator;

	@Autowired
	private SessionIdentifierService sessionIdentifierService;

	@Autowired
	private GameService gameService;

	@Autowired
	private CassinoService cassinoService;

	@Autowired
	private DefaultConstants constants;

	@Autowired
	private RoundService roundService;

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BetService betService;

	@PostMapping("/game/open")
	@ApiOperation(value = "Open a new Game")
	public ResponseEntity<Object> openGame(@RequestBody OpenGameDTO dto) {

		try {
			Optional<Player> player = findPlayerById(dto);
			Cassino cassino = generateAndSaveCassino();
			Game game = generateAndSaveGame(player, cassino);
			SessionIdentifier identifier = generateIdentifierSession(game.getId().toString());

			IdentifierDTO identifierDTO = IdentifierDTO.builder().passwordFromSessionId(identifier.getSessionId())
					.loginFromGameId(identifier.getGameId()).build();

			sessionIdentifierService.save(identifier);
			return new ResponseEntity<Object>(identifierDTO, HttpStatus.CREATED);
		} catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping("/game/play")
	@ApiOperation(value = "Simulate a round")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<Object> playGame(@RequestBody PlayGameDTO dto) {
		try {
			Optional<Player> player = playerService.findById(dto.getPlayerId());
			Optional<Game> game = gameService.findById(dto.getGameId());
			Optional<Round> round = roundService.findById(dto.getRoundId());
			Double betAmount = dto.getBetAmount();

			validateBet(player, game, round, betAmount);

			boolean playerWins = processSimulator.roundWins();

			Account account = player.get().getAccount();
			Double balance = player.get().getAccount().getBalance();
			if (playerWins) {
				account.setBalance(balance += betAmount);
			} else {
				account.setBalance(balance -= betAmount);
			}
			accountService.save(account);

			return new ResponseEntity<Object>(new RoundResultDTO().builder().win(playerWins).newBalance(account.getBalance()).build(), HttpStatus.OK);
			
		} catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	private void validateBet(Optional<Player> player, Optional<Game> game, Optional<Round> round, Double betAmount) {

		if (!player.isPresent()) {
			throw new BusinessRuleException(constants.PLAYER_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
		}
		if (!game.isPresent()) {
			throw new BusinessRuleException(constants.GAME_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
		}
		if (!round.isPresent()) {
			throw new BusinessRuleException(constants.ROUND_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
		}
		if (player.get().getAccount().getBalance() < betAmount) {
			throw new BusinessRuleException(constants.INSUFFICIENT_FUNDS_EXCEPTION_MESSAGE);
		}
		
		Bet bet = Bet.builder().amount(betAmount).round(round.get()).player(player.get()).build();
		betService.save(bet);
		
	}

	private Optional<Player> findPlayerById(OpenGameDTO dto) {
		Optional<Player> player = playerService.findById(dto.getPlayerId());
		if (!player.isPresent()) {
			throw new BusinessRuleException(constants.PLAYER_DOES_NOT_EXIST_EXCEPTION_MESSAGE);
		}
		return player;
	}

	private Game generateAndSaveGame(Optional<Player> player, Cassino cassino) {
		Game game = processSimulator.generateGame(player, cassino);
		gameService.save(game);
		return game;
	}

	private Cassino generateAndSaveCassino() {
		Cassino cassino = processSimulator.generateCassino();
		if (cassino.getId() == null) {
			cassinoService.save(cassino);
		}
		return cassino;
	}

	private SessionIdentifier generateIdentifierSession(String gameId) {
		return processSimulator.generateIdentifier(gameId);

	}

}
