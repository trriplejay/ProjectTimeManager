/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import com.google.common.collect.Iterables;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;

public class ProjectComputerTest extends AbstractComputerTest {
	private ProjectComputer testee = new ProjectComputer();

	@Override
	protected void setTesteeStore(ObjectStore<Booking> store) {
		testee.setStore(store);
	}

	@Test
	public void testProjectComputerFixed() {
		Collection<Collection<Object>> analysisResults = testee.analyze(Arrays.asList("2017-03"));
		assertEquals(4, analysisResults.size());
		assertEquals(100.0,
				Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 1), 3).toString().replaceAll(",", "."))
				+ Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 2), 3).toString().replaceAll(",", "."))
				+ Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 3), 3).toString().replaceAll(",", ".")),
				0.1);
	}

	@Test
	public void testProjectComputerDay() {
		Collection<Collection<Object>> analysisResults = testee.analyze(Arrays.asList("2017-03-15"));
		assertEquals(3, analysisResults.size());
		assertEquals(100.0, Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 1), 3).toString().replaceAll(",", "."))
				+ Double.parseDouble(Iterables.get(Iterables.get(analysisResults, 2), 3).toString().replaceAll(",", ".")), 0.1);
	}
}
