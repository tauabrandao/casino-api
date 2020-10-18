package com.tauabrandao.project.cassinoapi.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DefaultConstants {

	@Value("${cassinoapi.message.playerDoesNotExistExceptionMessage}")
	public String PLAYER_DOES_NOT_EXIST_EXCEPTION_MESSAGE;

	@Value("${cassinoapi.message.dateParseErrorMessage}")
	public String DATE_PARSER_ERROR_MESSAGE;

	
}
