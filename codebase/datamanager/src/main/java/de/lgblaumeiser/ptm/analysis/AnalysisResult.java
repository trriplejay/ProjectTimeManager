/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Collections.unmodifiableMap;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.Map;

/**
 * An interface returning the results of an analyis
 */
public class AnalysisResult {
    private final Map<String, Object> results = newHashMap();

    public void setResult(final String id, final Object result) {
	checkState(isNotBlank(id));
	checkNotNull(result);
	results.put(id, result);
    }

    public Map<String, Object> getResults() {
	return unmodifiableMap(results);
    }
}
