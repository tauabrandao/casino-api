package com.tauabrandao.project.cassinoapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPlayerDTO {
	
	private String name;
	private String dateOfBirth;
	private String email;
	private String gender;
	private String password;
	
}
