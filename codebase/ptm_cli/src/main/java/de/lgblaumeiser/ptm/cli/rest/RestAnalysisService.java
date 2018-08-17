/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.rest;

import de.lgblaumeiser.ptm.analysis.DataAnalysisService;

import java.util.Collection;

import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

/**
 * Rest Proxy Implementation for accessing analysis results over rest api.
 */
public class RestAnalysisService extends RestBaseService implements DataAnalysisService {
	@Override
	public Collection<Collection<Object>> analyze(String analyzerId, Collection<String> parameter) {
		Object[][] result = getRestUtils().get(
				"/analysis/" + analyzerId + "/" + get(parameter, 0) + "/" + get(parameter, 1), Object[][].class);
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
