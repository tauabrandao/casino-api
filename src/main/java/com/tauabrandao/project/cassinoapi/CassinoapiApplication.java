package com.tauabrandao.project.cassinoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("com.tauabrandao.project.cassinoapi")
public class CassinoapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CassinoapiApplication.class, args);
	}

}
