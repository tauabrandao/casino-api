package com.tauabrandao.project.cassinoapi.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class IdentifierDTO {
	
	private String loginFromGameId;
	private String passwordFromSessionId;

}
