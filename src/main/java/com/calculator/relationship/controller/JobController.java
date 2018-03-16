package com.calculator.relationship.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value="/relation")
public class JobController 
{
	private static Logger log = LoggerFactory.getLogger(JobController.class);

	@RequestMapping("/appState" )
	String appState(){
		return "OK" ;
	}

	
}
