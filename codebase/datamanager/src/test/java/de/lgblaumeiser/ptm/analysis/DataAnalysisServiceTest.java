/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis;

import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class DataAnalysisServiceTest {
	private DataAnalysisService testee;

	private static final String ANALYSISID = "testanalysis";
	private static final String PARAM1 = "Param 1";
	private static final String PARAM2 = "Param 2";

	@Before
	public void before() {
		DataAnalysisServiceImpl testSetup = new DataAnalysisServiceImpl();
		testSetup.addAnalysis(ANALYSISID, new Analysis() {
			@Override
			public Collection<Collection<Object>> analyze(Collection<String> parameter) {
				Collection<Object> returnParam = newArrayList(parameter);
				return asList(returnParam);
			}
		});
		testee = testSetup;
	}

	@Test
	public void testDataAnalysisServiceClean() {
		Collection<Collection<Object>> result = testee.analyze(ANALYSISID, asList(PARAM1, PARAM2));
		assertEquals(1, result.size());
		Collection<Object> content = get(result, 0);
		assertEquals(2, content.size());
		assertTrue(content.contains(PARAM1));
		assertTrue(content.contains(PARAM2));

	}

	@Test(expected = IllegalStateException.class)
	public void testDataAnalysisServiceUnknownId() {
		testee.analyze(ANALYSISID + PARAM1, emptyList());
	}

	@Test(expected = IllegalStateException.class)
	public void testDataAnalysisServiceEmptyId() {
		testee.analyze(EMPTY, emptyList());
	}

	@Test(expected = IllegalStateException.class)
	public void testDataAnalysisServiceNullId() {
		testee.analyze(null, emptyList());
	}

	@Test(expected = IllegalStateException.class)
	public void testDataAnalysisServiceNullParam() {
		testee.analyze(ANALYSISID, null);
	}
}
