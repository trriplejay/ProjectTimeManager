package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class OpenDayTest extends AbstractHandlerTest {
	private OpenDay testee = new OpenDay();
	
	@Test
	public void testOpenDayNoParam() {
		testee.handleCommand(emptyList());
		assertEquals(LocalDate.now(), services.getStateStore().getCurrentDay().getDay());
	}
	
	@Test
	public void testOpenDayOneParamFromRepo() {
		testee.handleCommand(asList(DATE1.toString()));
		assertTrue(testDay == services.getStateStore().getCurrentDay());
	}

	@Test
	public void testOpenDayOneParamFresh() {
		LocalDate testDate = LocalDate.now().minusWeeks(1);
		testee.handleCommand(asList(testDate.toString()));
		assertEquals(testDate, services.getStateStore().getCurrentDay().getDay());
	}

	@Test(expected = DateTimeParseException.class)
	public void testOpenDayOneWrongParam() {
		testee.handleCommand(asList(ACTIVITY1NAME));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testOpenDayOneEmptyParam() {
		testee.handleCommand(asList(StringUtils.EMPTY));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testOpenDayOneNullParam() {
		testee.handleCommand(asList((String)null));
	}
}
