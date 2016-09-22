/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis;

import java.util.Collection;

/**
 * Interface to be implemented for an anaylsis of the data
 */
public interface Analysis {
    AnalysisResult analyze(Collection<String> parameter);
}
