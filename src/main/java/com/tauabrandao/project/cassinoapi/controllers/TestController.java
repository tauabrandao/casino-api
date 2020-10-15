package com.tauabrandao.project.cassinoapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Test Controller")
public class TestController {

	@GetMapping("/test")
	@ApiOperation(value = "End point test")
	public String testController() {
		return "Working properly";
	}
	
}
