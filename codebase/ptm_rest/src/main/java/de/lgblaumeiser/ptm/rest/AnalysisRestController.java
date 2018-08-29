/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Restcontroller for running analysis
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisRestController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ServiceMapper services;

	@RequestMapping(method = RequestMethod.GET, value = "/{analyzerId}/{timeframe}/{param}",
			consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	Collection<Collection<Object>> runAnalysis(@PathVariable String analyzerId, @PathVariable String timeframe, @PathVariable String param) {
		logger.info("Request: Get Analysis Data for " + analyzerId + " for time frame " + timeframe + " " + param);
		return services.analysisService().analyze(analyzerId.toUpperCase(), asList(timeframe, param));
	}

	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<?> handleException(IllegalStateException e) {
		logger.error("Exception in Request", e);
		return ResponseEntity.status(BAD_REQUEST).body(e.toString());
	}
}
