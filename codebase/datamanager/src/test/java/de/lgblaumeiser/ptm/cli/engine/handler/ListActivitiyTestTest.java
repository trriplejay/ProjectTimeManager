package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ListActivitiyTestTest extends AbstractHandlerTest {
	private ListActivity testee = new ListActivity();

	@Test
	public void testListActivity() {
		services.getActivityService().addLineActivity(ACTIVITY1NAME, ACTIVITY1NUMBER);
		testee.handleCommand(emptyList());
		assertTrue(logger.logMessages.toString().contains(ACTIVITY1NAME));
		assertTrue(logger.logMessages.toString().contains(ACTIVITY1NUMBER));
	}
}
