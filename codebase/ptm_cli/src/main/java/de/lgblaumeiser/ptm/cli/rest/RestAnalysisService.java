/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.rest;

import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import de.lgblaumeiser.ptm.analysis.DataAnalysisService;

/**
 * Rest Proxy Implementation for accessing analysis results over rest api.
 */
public class RestAnalysisService extends RestBaseService implements DataAnalysisService {
	@Override
	public Collection<Collection<Object>> analyze(String analyzerId, Collection<String> parameter) {
		YearMonth month = YearMonth.now();
		if (!parameter.isEmpty()) {
			month = YearMonth.parse(get(parameter, 0));
		}
		Object[][] result = getRestUtils().get(
				"/analysis/" + analyzerId + "/" + month.format(DateTimeFormatter.ofPattern("yyyy-MM")),
				Object[][].class);
		return convertToCollection(result);
	}

	private Collection<Collection<Object>> convertToCollection(Object[][] resultData) {
		Collection<Collection<Object>> converted = newArrayList();
		for (Object[] currentLine : resultData) {
			converted.add(asList(currentLine));
		}
		return converted;
	}
}
