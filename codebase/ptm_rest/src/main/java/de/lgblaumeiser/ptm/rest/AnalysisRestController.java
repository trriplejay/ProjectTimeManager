/*
 * Copyright 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.rest;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Restcontroller for running analysis
 */
@RestController
@RequestMapping("/analysis")
public class AnalysisRestController {
	@Autowired
	private ServiceMapper services;

	@RequestMapping(method = RequestMethod.GET, value = "/{analyzerId}/{param}")
	Collection<Collection<Object>> runAnalysis(@PathVariable String analyzerId, @PathVariable String param) {
		return services.analysisService().analyze(analyzerId.toUpperCase(), asList(param));
	}
}
