/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import org.junit.Before;

public class DeleteBookingTest extends AbstractHandlerTest {
	private DeleteBooking testee = new DeleteBooking();

	@Override
	@Before
	public void before() {
		super.before();
	}
	//
	// @Test
	// public void testDeleteBookingClean() {
	// services.getBookingService().addBooking(services.getStateStore().getCurrentDay(),
	// ACTIVITY1, TIME1);
	// assertEquals(1, services.getBookingsStore().retrieveAll().size());
	// assertEquals(1L, get(services.getBookingsStore().retrieveAll(),
	// 0).getId().longValue());
	// testee.handleCommand(asList("1"));
	// assertEquals(0, services.getBookingsStore().retrieveAll().size());
	// }
	//
	// @Test(expected = IllegalStateException.class)
	// public void testDeleteBookingNoParam() {
	// testee.handleCommand(emptyList());
	// }
	//
	// @Test(expected = RuntimeException.class)
	// public void testDeleteBookingEmptyParam() {
	// testee.handleCommand(asList(StringUtils.EMPTY));
	// }
}
