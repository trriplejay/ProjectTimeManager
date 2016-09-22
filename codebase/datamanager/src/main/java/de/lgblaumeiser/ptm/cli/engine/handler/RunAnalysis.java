/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.base.Preconditions.checkState;

import java.util.Collection;
import java.util.Map.Entry;

import com.google.common.collect.Iterables;

import de.lgblaumeiser.ptm.analysis.AnalysisResult;
import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;

/**
 * Run an analysis on the data
 */
public class RunAnalysis extends AbstractCommandHandler {
    @Override
    public void handleCommand(final Collection<String> parameters) {
	checkState(parameters.size() > 0);
	String analysis = Iterables.get(parameters, 0);
	parameters.remove(analysis);
	AnalysisResult result = getServices().getAnalysisService().analyze(analysis.toUpperCase(), parameters);
	getLogger().log("Run analysis " + analysis + " on data ...");
	for (Entry<String, Object> current : result.getResults().entrySet()) {
	    getLogger().log(current.getKey() + ": " + current.getValue());
	}
	getLogger().log("... analysis done");
    }

    @Override
    public String toString() {
	return "Runs an analysis on the given data, Params: <1> Analysis ID, <2+> Params of specified analyis";
    }
}
