package de.lgblaumeiser.ptm.analysis.analyzer;

import static com.google.common.collect.Iterables.get;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.store.ObjectStore;

public class HourComputerTest extends AbstractComputerTest {
	private HourComputer testee = new HourComputer();
	
	protected void setTesteeStore(ObjectStore<DayBookings> store) {
		testee.setStore(store);
	}
	
	@Test
	public void testHourComputerToday() {
		Collection<Collection<Object>> analysisResults = testee.analyze(emptyList());
		assertEquals(4, analysisResults.size());
		assertEquals("-10:13", get(get(analysisResults, 3), 3));
	}

	@Test
	public void testHourComputerFixed() {
		Collection<Collection<Object>> analysisResults = testee.analyze(asList("2015-12"));
		System.out.println(analysisResults);
		assertEquals(4, analysisResults.size());
		assertEquals(" 10:43", get(get(analysisResults, 3), 3));
	}	
}
