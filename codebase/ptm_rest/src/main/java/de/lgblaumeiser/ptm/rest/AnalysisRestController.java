/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static java.util.Arrays.asList;

/**
 * Restcontroller for running analysis
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisRestController {
	@Autowired
	private ServiceMapper services;

	@RequestMapping(method = RequestMethod.GET, value = "/{analyzerId}/{param}",
			consumes= MediaType.APPLICATION_JSON_UTF8_VALUE,
			produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	Collection<Collection<Object>> runAnalysis(@PathVariable String analyzerId, @PathVariable String param) {
		return services.analysisService().analyze(analyzerId.toUpperCase(), asList(param));
	}
}
