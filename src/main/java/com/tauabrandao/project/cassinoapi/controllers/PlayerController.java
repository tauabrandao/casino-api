package com.tauabrandao.project.cassinoapi.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tauabrandao.project.cassinoapi.constants.DefaultConstants;
import com.tauabrandao.project.cassinoapi.dto.FullPlayerDataDTO;
import com.tauabrandao.project.cassinoapi.dto.NewPlayerDTO;
import com.tauabrandao.project.cassinoapi.enums.Gender;
import com.tauabrandao.project.cassinoapi.exception.BusinessRuleException;
import com.tauabrandao.project.cassinoapi.model.Account;
import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.service.AccountService;
import com.tauabrandao.project.cassinoapi.service.PlayerService;
import com.tauabrandao.project.cassinoapi.util.ProcessSimulator;

@RestController
public class PlayerController {

	@Autowired
	private ProcessSimulator processSimulator;

	@Autowired
	private AccountService accountService;

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private DefaultConstants constants;

	@PostMapping("/player/new")
	public ResponseEntity<Object> createPlayer(@RequestBody final NewPlayerDTO playerDTO) {
		try {
			playerService.validatePlayerEmail(playerDTO.getEmail());
			Player player = generatePlayer(playerDTO);
			Account account = generateAndSaveNewAccount(player);
			player.setAccount(account);
			playerService.save(player);
			return new ResponseEntity<Object>(player, HttpStatus.CREATED);
		} catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (DateTimeParseException e) {
			return ResponseEntity.badRequest().body(constants.DATE_PARSER_ERROR_MESSAGE);
		}
	}

	private Player generatePlayer(final NewPlayerDTO playerDTO) {
		
		return playerService.save(extractPlayerFromDTO(playerDTO));
	}

	@GetMapping("/player/{playerId}")
	public ResponseEntity<Object> getPlayerDetails(@PathVariable("playerId") String playerId) {

		if (!playerService.existsById(Long.parseLong(playerId))) {
			return ResponseEntity.badRequest().body(constants.PLAYER_DOES_NOT_EXIST_EXCEPTION_MESSAGE);

		}
		FullPlayerDataDTO playerData = playerService.getFullPlayerDataByPlayerId(Long.parseLong(playerId));
		return new ResponseEntity<Object>(playerData, HttpStatus.OK);

	}

	private Account generateAndSaveNewAccount(Player player) {
		Account account = processSimulator.generateAccount(player);
		accountService.save(account);
		return account;
	}

	private Player extractPlayerFromDTO(final NewPlayerDTO dto) {
		return Player.builder().name(dto.getName()).email(dto.getEmail()).password(dto.getPassword())
				.dateOfBirth(LocalDate.parse(dto.getDateOfBirth())).gender(getGenderFromDTO(dto)).build();
	}

	private Gender getGenderFromDTO(final NewPlayerDTO dto) {
		Gender gender;
		if (Gender.MALE.toString().equals(dto.getGender())) {
			gender = Gender.MALE;
		} else if (Gender.FEMALE.toString().equals(dto.getGender())) {
			gender = Gender.FEMALE;
		} else {
			gender = Gender.OTHER;
		}
		return gender;
	}

}
