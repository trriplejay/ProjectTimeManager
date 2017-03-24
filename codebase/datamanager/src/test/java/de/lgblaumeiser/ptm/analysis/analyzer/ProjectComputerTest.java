/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import static com.google.common.collect.Iterables.get;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
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
		Collection<Collection<Object>> analysisResults = testee.analyze(asList("2017-03"));
		System.out.println(analysisResults);
		assertEquals(3, analysisResults.size());
		assertEquals(40, parseInt(get(get(analysisResults, 1), 3).toString())
				+ parseInt(get(get(analysisResults, 2), 3).toString()));
	}
}
