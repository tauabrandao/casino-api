package com.tauabrandao.project.cassinoapi.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tauabrandao.project.cassinoapi.dto.PlayerDTO;
import com.tauabrandao.project.cassinoapi.enums.Gender;
import com.tauabrandao.project.cassinoapi.exception.BusinessRuleException;
import com.tauabrandao.project.cassinoapi.model.Account;
import com.tauabrandao.project.cassinoapi.model.Player;
import com.tauabrandao.project.cassinoapi.service.AccountService;
import com.tauabrandao.project.cassinoapi.service.PlayerService;
import com.tauabrandao.project.cassinoapi.util.ProcessSimulator;

@RestController
public class PlayerController {

	@Value("${cassinoapi.message.dateParseErrorMessage}")
	private String DATE_PARSER_ERROR_MESSAGE;

	@Autowired
	ProcessSimulator processSimulator;

	@Autowired
	AccountService accountService;

	@Autowired
	PlayerService playerService;

	@PostMapping("/player/new")
	public ResponseEntity<Object> createPlayer(@RequestBody final PlayerDTO playerDTO) {
		try {
			playerService.validatePlayerEmail(playerDTO.getEmail());
			Account account = generateAndSaveNewAccount();
			return new ResponseEntity<Object>(playerService.save(extractPlayerFromDTO(playerDTO, account)),
					HttpStatus.CREATED);
		} catch (BusinessRuleException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (DateTimeParseException e) {
			return ResponseEntity.badRequest().body(DATE_PARSER_ERROR_MESSAGE);
		}
	}

	private Account generateAndSaveNewAccount() {
		Account account = processSimulator.generateAccount();
		accountService.save(account);
		return account;
	}

	private Player extractPlayerFromDTO(final PlayerDTO dto, final Account account) {
		return Player.builder().name(dto.getName()).email(dto.getEmail()).password(dto.getPassword()).account(account)
				.dateOfBirth(LocalDate.parse(dto.getDateOfBirth())).gender(getGenderFromDTO(dto)).build();
	}

	private Gender getGenderFromDTO(final PlayerDTO dto) {
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
