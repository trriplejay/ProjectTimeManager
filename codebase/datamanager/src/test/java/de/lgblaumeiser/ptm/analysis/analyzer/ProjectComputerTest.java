package de.lgblaumeiser.ptm.analysis.analyzer;

import static com.google.common.collect.Iterables.get;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.DayBookings;
import de.lgblaumeiser.store.ObjectStore;

public class ProjectComputerTest extends AbstractComputerTest {
	private ProjectComputer testee = new ProjectComputer();

	protected void setTesteeStore(ObjectStore<DayBookings> store) {
		testee.setStore(store);
	}
		
	@Test
	public void testProjectComputerToday() {
		Collection<Collection<Object>> analysisResults = testee.analyze(emptyList());
		assertEquals(3, analysisResults.size());
		assertEquals(24, parseInt(get(get(analysisResults, 1), 3).toString()) + parseInt(get(get(analysisResults, 2), 3).toString()));
	}

	@Test
	public void testProjectComputerFixed() {
		Collection<Collection<Object>> analysisResults = testee.analyze(asList("2015-12"));
		assertEquals(3, analysisResults.size());
		assertEquals(24, parseInt(get(get(analysisResults, 1), 3).toString()) + parseInt(get(get(analysisResults, 2), 3).toString()));
	}
}
