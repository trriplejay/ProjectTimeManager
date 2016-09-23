/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;

/**
 * An interface returning the results of an analyis
 */
public class AnalysisResult {
    private final Collection<Collection<Object>> results = newArrayList();

    public void setResult(final Collection<Object> result) {
	checkNotNull(result);
	results.add(result);
    }

    public Collection<Collection<Object>> getResults() {
	return unmodifiableCollection(results);
    }
}
