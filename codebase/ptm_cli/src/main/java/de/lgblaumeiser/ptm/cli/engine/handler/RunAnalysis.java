/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.get;

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
		parameters.remove(analysis);
		getLogger().log("Run analysis " + analysis + " on data ...");
		Collection<Collection<Object>> result = getServices().getAnalysisService().analyze(analysis.toUpperCase(),
				parameters);
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
