/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RunAnalysisTest extends AbstractHandlerTest {
	private RunAnalysis testee = new RunAnalysis();

	private static final String ANALYSIS = "MyAnalysis";
	private static final String PARAM1 = "Param1";
	private static final String PARAM2 = "Param2";

	@Test
	public void testRunAnalysisClean() {
		testee.handleCommand(newArrayList(ANALYSIS, PARAM1, PARAM2));
		String logMessages = logger.logMessages.toString();
		String[] lines = logMessages.split("xxxnewlinexxx");
		assertEquals(4, lines.length);
		assertTrue(lines[1].contains(ANALYSIS.toUpperCase()));
		assertTrue(lines[2].contains(PARAM1));
		assertTrue(lines[2].contains(PARAM2));
	}

	@Test(expected = IllegalStateException.class)
	public void testRunAnalysisNoParams() {
		testee.handleCommand(emptyList());
	}
}
