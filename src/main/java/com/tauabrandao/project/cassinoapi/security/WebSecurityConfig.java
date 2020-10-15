package com.tauabrandao.project.cassinoapi.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String LOGIN_PATH = "/authenticate";
	private static final String API_DOCS_PATH = "/v2/api-docs";
	private static final String UI_CONFIGURATION_PATH = "/configuration/ui";
	private static final String SWAGGER_RESOURCES = "/swagger-resources/**";
	private static final String CONFIGURATION = "/configuration/**";
	private static final String SWAGGER_UI_HTML = "/swagger-ui.html";
	private static final String WEBJARS = "/webjars/**";

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests()
				.antMatchers(LOGIN_PATH, API_DOCS_PATH, UI_CONFIGURATION_PATH, SWAGGER_RESOURCES, CONFIGURATION,
						SWAGGER_UI_HTML, WEBJARS)
				.permitAll().anyRequest().authenticated().and()
				.addFilterBefore(new JWTLoginFilter(LOGIN_PATH, authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)

				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authentication) throws Exception {		
		authentication.inMemoryAuthentication().withUser("username").password("{noop}password").roles("ADMIN");
	}
}