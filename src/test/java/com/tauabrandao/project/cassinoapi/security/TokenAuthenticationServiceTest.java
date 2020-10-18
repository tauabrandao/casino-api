package com.tauabrandao.project.cassinoapi.security;

import java.net.URI;
import java.net.URISyntaxException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.tauabrandao.project.cassinoapi.CassinoapiApplication;
import com.tauabrandao.project.cassinoapi.dto.IdentifierDTO;
import com.tauabrandao.project.cassinoapi.dto.NewPlayerDTO;
import com.tauabrandao.project.cassinoapi.dto.OpenGameDTO;
import com.tauabrandao.project.cassinoapi.dto.UserAuthenticationDTO;
import com.tauabrandao.project.cassinoapi.model.Player;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = CassinoapiApplication.class)
public class TokenAuthenticationServiceTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void mustAuthenticateTheUser() throws URISyntaxException {

		ResponseEntity<Player> newPlayerResult = saveNewPlayer();
		Player p = newPlayerResult.getBody();
		ResponseEntity<IdentifierDTO> newGameResult = openNewGame(p);
		IdentifierDTO i = newGameResult.getBody();

		UserAuthenticationDTO authentication = UserAuthenticationDTO.builder().username(i.getLoginFromGameId())
				.password(i.getPasswordFromSessionId()).build();
		final String authenticationUrl = getAuthenticationUrl();
		URI uri = new URI(authenticationUrl);
		HttpEntity<UserAuthenticationDTO> authenticationRequest = new HttpEntity<UserAuthenticationDTO>(authentication);
		ResponseEntity<String> authenticationResult = this.restTemplate.postForEntity(uri, authenticationRequest,
				String.class);

		Assertions.assertThat(authenticationResult.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(authenticationResult.getBody()).isNotEmpty();

	}

	private ResponseEntity<IdentifierDTO> openNewGame(Player p) throws URISyntaxException {
		URI uri;
		OpenGameDTO newGameDto = OpenGameDTO.builder().playerId(p.getId()).build();
		final String newGameUrl = getNewGameUrl();
		uri = new URI(newGameUrl);
		HttpEntity<OpenGameDTO> newGameRequest = new HttpEntity<OpenGameDTO>(newGameDto);
		ResponseEntity<IdentifierDTO> newGameResult = this.restTemplate.postForEntity(uri, newGameRequest,
				IdentifierDTO.class);
		return newGameResult;
	}

	private ResponseEntity<Player> saveNewPlayer() throws URISyntaxException {
		NewPlayerDTO playerDto = NewPlayerDTO.builder().name("John").email("john@email.com").password("123")
				.dateOfBirth("1990-10-10").gender("MALE").build();
		final String newPlayerUrl = getNewPlayerUrl();
		URI uri = new URI(newPlayerUrl);
		HttpEntity<NewPlayerDTO> newPlayerRequest = new HttpEntity<NewPlayerDTO>(playerDto);
		ResponseEntity<Player> newPlayerResult = this.restTemplate.postForEntity(uri, newPlayerRequest, Player.class);
		return newPlayerResult;
	}

	@Test
	public void test() {

		Assertions.assertThat(1).isEqualTo(1);
	}

	private String getNewPlayerUrl() {
		return "http://localhost:" + port + "/player/new";
	}

	private String getNewGameUrl() {
		return "http://localhost:" + port + "/game/open";
	}

	private String getAuthenticationUrl() {
		return "http://localhost:" + port + "/authenticate";
	}
}
