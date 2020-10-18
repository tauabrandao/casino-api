package com.tauabrandao.project.cassinoapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private final String AUTH_POINT = "/authenticate";
	private final String OPEN_GAME = "/game/open";
	private final String API_DOCS_PATH = "/v2/api-docs";
	private final String UI_CONFIGURATION_PATH = "/configuration/ui";
	private final String SWAGGER_RESOURCES = "/swagger-resources/**";
	private final String CONFIGURATION = "/configuration/**";
	private final String SWAGGER_UI_HTML = "/swagger-ui.html";
	private final String WEBJARS = "/webjars/**";
	private final String NEW_PLAYER = "/player/new";

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests()
				.antMatchers(NEW_PLAYER, OPEN_GAME, AUTH_POINT, API_DOCS_PATH, UI_CONFIGURATION_PATH, SWAGGER_RESOURCES,
						CONFIGURATION, SWAGGER_UI_HTML, WEBJARS)
				.permitAll().anyRequest().authenticated().and()
				.addFilterBefore(new JWTLoginFilter(AUTH_POINT, authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)

				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authentication) throws Exception {
		authentication.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
}