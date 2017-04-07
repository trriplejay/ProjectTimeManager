/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.get;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

/**
 * Run an analysis on the data
 */
public class RunAnalysis extends AbstractCommandHandler {
	@Override
	public void handleCommand(final Collection<String> parameters) {
		checkState(parameters.size() > 0);
		String analysis = get(parameters, 0);
		YearMonth month = YearMonth.now();
		if (parameters.size() > 1) {
			month = YearMonth.parse(get(parameters, 1));
		}
		getLogger().log("Run analysis " + analysis + " on data ...");
		Collection<Collection<Object>> result = getServices().getRestUtils()
				.getAnalysisResult(month.format(DateTimeFormatter.ISO_LOCAL_DATE), analysis.toUpperCase());
		for (Collection<Object> current : result) {
			getLogger().log(createString(current));
		}
		getLogger().log("... analysis done");
	}

	private String createString(final Collection<Object> columns) {
		StringBuilder resultString = new StringBuilder();
		resultString.append("| ");
		for (Object current : columns) {
			resultString.append(current);
			resultString.append("\t | ");
		}
		return resultString.toString();
	}

	@Override
	public String toString() {
		return "Runs an analysis on the given data, Params: <1> Analysis ID, <2+> Params of specified analyis";
	}
}
