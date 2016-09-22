/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
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
    AnalysisResult analyze(String analyzerId, Collection<String> parameter);
}
