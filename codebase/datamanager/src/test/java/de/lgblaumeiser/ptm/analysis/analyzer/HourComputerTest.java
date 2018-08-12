/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;
import org.junit.Test;

import java.util.Collection;

import static com.google.common.collect.Iterables.get;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

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
		assertEquals("-07:30", get(get(analysisResults, 5), 7));
	}
}
