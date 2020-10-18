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
public class PlayGameDTO {

	private Long roundId;
	private Long playerId;
	private Long gameId;
	private Double betAmount;
	
}
