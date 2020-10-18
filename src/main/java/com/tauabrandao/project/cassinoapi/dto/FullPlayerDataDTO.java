package com.tauabrandao.project.cassinoapi.dto;

import java.time.LocalDate;

import com.tauabrandao.project.cassinoapi.enums.Gender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullPlayerDataDTO {

	private String name;
	private LocalDate dateOfBirth;
	private String email;
	private Gender gender;
	public Long playerId;
	public Double balance;
	public String currency;

	public FullPlayerDataDTO(String name, LocalDate dateOfBirth, String email, Gender gender,
			Long playerId, Double balance, String currency) {
		super();
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.email = email;
		this.gender = gender;
		this.playerId = playerId;
		this.balance = balance;
		this.currency = currency;
	}

}
