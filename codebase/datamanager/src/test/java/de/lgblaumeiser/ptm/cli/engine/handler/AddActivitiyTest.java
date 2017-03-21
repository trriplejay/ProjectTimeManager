package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class AddActivitiyTest extends AbstractHandlerTest {
	private AddActivity testee = new AddActivity();

	@Test
	public void testAddActivityTwoParamClean() {
		testee.handleCommand(asList(ACTIVITY1NAME, ACTIVITY1NUMBER));
		assertTrue(activityStoreCalled);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddActivityTwoParamFirstNull() {
		testee.handleCommand(asList(null, ACTIVITY1NUMBER));
	}

	@Test(expected = IllegalStateException.class)
	public void testAddActivityTwoParamFirstEmpty() {
		testee.handleCommand(asList(StringUtils.EMPTY, ACTIVITY1NUMBER));
	}

	@Test(expected = IllegalStateException.class)
	public void testAddActivityTwoParamSecondNull() {
		testee.handleCommand(asList(ACTIVITY1NAME, null));
	}

	@Test(expected = IllegalStateException.class)
	public void testAddActivityTwoParamSecondEmpty() {
		testee.handleCommand(asList(ACTIVITY1NAME, StringUtils.EMPTY));
	}

	@Test(expected = IllegalStateException.class)
	public void testAddActivityOneParam() {
		testee.handleCommand(asList(ACTIVITY1NAME));
	}

	@Test(expected = IllegalStateException.class)
	public void testAddActivityNoParam() {
		testee.handleCommand(emptyList());
	}
}
