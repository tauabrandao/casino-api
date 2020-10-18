package com.tauabrandao.project.cassinoapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewPlayerDTO {
	
	private String name;
	private String dateOfBirth;
	private String email;
	private String gender;
	private String password;
	
}