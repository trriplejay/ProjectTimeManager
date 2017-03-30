/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class OpenDayTest extends AbstractHandlerTest {
	private OpenDay testee = new OpenDay();

	@Test
	public void testOpenDayNoParam() {
		testee.handleCommand(emptyList());
		assertEquals(now(), services.getStateStore().getCurrentDay());
	}

	@Test
	public void testOpenDayOneParamFromRepo() {
		testee.handleCommand(asList(DATE1.toString()));
		assertEquals(DATE1, services.getStateStore().getCurrentDay());
	}

	@Test
	public void testOpenDayOneParamFresh() {
		LocalDate testDate = DATE1.minusWeeks(1);
		testee.handleCommand(asList(testDate.toString()));
		assertEquals(testDate, services.getStateStore().getCurrentDay());
	}

	@Test(expected = DateTimeParseException.class)
	public void testOpenDayOneWrongParam() {
		testee.handleCommand(asList(ACTIVITY1NAME));
	}

	@Test(expected = DateTimeParseException.class)
	public void testOpenDayOneEmptyParam() {
		testee.handleCommand(asList(StringUtils.EMPTY));
	}

	@Test(expected = NullPointerException.class)
	public void testOpenDayOneNullParam() {
		testee.handleCommand(asList((String) null));
	}
}
