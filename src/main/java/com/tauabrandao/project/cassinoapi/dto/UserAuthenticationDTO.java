package com.tauabrandao.project.cassinoapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserAuthenticationDTO {

	private String username;
	private String password;
	
}
