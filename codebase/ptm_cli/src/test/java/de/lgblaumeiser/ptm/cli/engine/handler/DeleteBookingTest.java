/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.collect.Iterables.get;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class DeleteBookingTest extends AbstractHandlerTest {
	private DeleteBooking testee = new DeleteBooking();

	@Override
	@Before
	public void before() {
		super.before();
	}

	@Test
	public void testDeleteBookingClean() {
		services.getBookingService().addBooking(services.getStateStore().getCurrentDay(), USER, ACTIVITY1, TIME1, "");
		assertEquals(1, services.getBookingsStore().retrieveAll().size());
		assertEquals(1L, get(services.getBookingsStore().retrieveAll(), 0).getId().longValue());
		testee.handleCommand(asList("1"));
		assertEquals(0, services.getBookingsStore().retrieveAll().size());
	}

	@Test(expected = IllegalStateException.class)
	public void testDeleteBookingNoParam() {
		testee.handleCommand(emptyList());
	}

	@Test(expected = RuntimeException.class)
	public void testDeleteBookingEmptyParam() {
		testee.handleCommand(asList(StringUtils.EMPTY));
	}
}
