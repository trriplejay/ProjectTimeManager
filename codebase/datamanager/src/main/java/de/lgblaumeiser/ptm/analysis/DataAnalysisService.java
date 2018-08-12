/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.analysis;

import java.util.Collection;

/**
 * Service interface that allows to run analysis on the data
 */
public interface DataAnalysisService {
	/**
	 * Run an analysis with id analyzerId and the given parameters
	 *
	 * @param analyzerId
	 *            The id of the analyzer to run
	 * @param parameter
	 *            Parameters given to the analyzer
	 * @return The result as an implemented analysis result interface
	 */
	Collection<Collection<Object>> analyze(String analyzerId, Collection<String> parameter);
}
