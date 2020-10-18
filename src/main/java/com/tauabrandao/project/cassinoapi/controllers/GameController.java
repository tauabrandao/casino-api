package com.tauabrandao.project.cassinoapi.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tauabrandao.project.cassinoapi.constants.DefaultConstants;
import com.tauabrandao.project.cassinoapi.dto.IdentifierDTO;
import com.tauabrandao.project.cassinoapi.dto.OpenGameDTO;
import com.tauabrandao.project.cassinoapi.exception.BusinessRuleException;
import com.tauabrandao.project.cassinoapi.model.Cassino;
import com.tauabrandao.project.cassinoapi.model.Game;
import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.model.SessionIdentifier;
import com.tauabrandao.project.cassinoapi.service.CassinoService;
import com.tauabrandao.project.cassinoapi.service.GameService;
import com.tauabrandao.project.cassinoapi.service.PlayerService;
import com.tauabrandao.project.cassinoapi.service.SessionIdentifierService;
import com.tauabrandao.project.cassinoapi.util.ProcessSimulator;

@RestController
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

	@PostMapping("/game/open")
	private ResponseEntity<Object> openGame(@RequestBody OpenGameDTO dto) {

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
