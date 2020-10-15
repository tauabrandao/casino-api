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

import com.tauabrandao.project.cassinoapi.security.dto.UserAuthenticationDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthenticationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	

	@Test
	public void mustAuthenticateTheUser() throws URISyntaxException {
		final String baseUrl = getAuthenticationURL();
		URI uri = new URI(baseUrl);
		UserAuthenticationDTO dto = UserAuthenticationDTO.builder().username("username").password("password").build();
		HttpEntity<UserAuthenticationDTO> request = new HttpEntity<UserAuthenticationDTO>(dto);
		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
		Assertions.assertThat(result.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(result.getBody()).isNotEmpty();
	}


	private String getAuthenticationURL() {
		return "http://localhost:" + port + "/authenticate";
	}

}
