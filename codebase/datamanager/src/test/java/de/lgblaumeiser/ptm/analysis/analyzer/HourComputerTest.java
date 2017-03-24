/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import static com.google.common.collect.Iterables.get;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;

public class HourComputerTest extends AbstractComputerTest {
	private final HourComputer testee = new HourComputer();

	@Override
	protected void setTesteeStore(final ObjectStore<Booking> store) {
		testee.setStore(store);
	}

	@Test
	public void testHourComputerToday() {
		Collection<Collection<Object>> analysisResults = testee.analyze(asList("2017-03"));
		assertEquals(6, analysisResults.size());
		assertEquals("-11:15", get(get(analysisResults, 5), 5));
	}
}
