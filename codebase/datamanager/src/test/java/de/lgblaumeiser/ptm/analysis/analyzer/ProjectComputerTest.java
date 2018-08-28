/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import com.google.common.collect.Iterables;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

public class ProjectComputerTest extends AbstractComputerTest {
	private ProjectComputer testee;

	@Override
	protected void createTestee(ObjectStore<Booking> store) {
		testee = new ProjectComputer(store);
	}

	@Test
	public void testProjectComputerFixed() {
		Collection<Collection<Object>> analysisResults = testee.analyze(Arrays.asList("month", "2017-03"));
		assertEquals(5, analysisResults.size());
		assertEquals(200.0,
				Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 1), 3).toString()
						.replaceAll(",", ".").replaceAll("%", ""))
				+ Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 2), 3).toString()
						.replaceAll(",", ".").replaceAll("%", ""))
				+ Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 3), 3).toString()
						.replaceAll(",", ".").replaceAll("%", ""))
				+ Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 4), 3).toString()
						.replaceAll(",", ".").replaceAll("%", "")),
				0.1);
	}

	@Test
	public void testProjectComputerDay() {
		Collection<Collection<Object>> analysisResults = testee.analyze(Arrays.asList("day", "2017-03-15"));
		assertEquals(4, analysisResults.size());
		assertEquals(200.0,
				Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 1), 3).toString()
						.replaceAll(",", ".").replaceAll("%", ""))
				+ Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 2), 3).toString()
						.replaceAll(",", ".").replaceAll("%", ""))
				+ Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 3), 3).toString()
						.replaceAll(",", ".").replaceAll("%", "")),
				0.1);
	}

	@Test
	public void testProjectComputerWeek() {
		Collection<Collection<Object>> analysisResults = testee.analyze(Arrays.asList("week", "2017-03-09"));
		assertEquals(5, analysisResults.size());
		assertEquals(200.0,
				Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 1), 3).toString()
						.replaceAll(",", ".").replaceAll("%", ""))
				+ Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 2), 3).toString()
						.replaceAll(",", ".").replaceAll("%", ""))
				+ Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 3), 3).toString()
						.replaceAll(",", ".").replaceAll("%", ""))
				+ Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 4), 3).toString()
						.replaceAll(",", ".").replaceAll("%", "")),
				0.15);
	}
}
