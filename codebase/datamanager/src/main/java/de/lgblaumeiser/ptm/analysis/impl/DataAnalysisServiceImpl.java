/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.analysis.impl;

import static de.lgblaumeiser.ptm.util.Utils.assertState;
import static de.lgblaumeiser.ptm.util.Utils.stringHasContent;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.lgblaumeiser.ptm.analysis.Analysis;
import de.lgblaumeiser.ptm.analysis.DataAnalysisService;

/**
 * Service implementation
 */
public class DataAnalysisServiceImpl implements DataAnalysisService {
	private final Map<String, Analysis> analysisStore = new HashMap<>();

	@Override
	public Collection<Collection<Object>> analyze(final String analyzerId, final Collection<String> parameter) {
		assertState(stringHasContent(analyzerId));
		assertState(parameter != null);
		Analysis analysis = analysisStore.get(analyzerId);
		assertState(analysis != null);
		return analysis.analyze(parameter);
	}

	public DataAnalysisServiceImpl addAnalysis(final String id, final Analysis analysis) {
		analysisStore.put(id, analysis);
		return this;
	}
}
