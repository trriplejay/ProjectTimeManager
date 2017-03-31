/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

public class AddBookingTest extends AbstractHandlerTest {
	private AddBooking testee = new AddBooking();
	//
	// @Test
	// public void testAddBookingTwoParamClean() {
	// testee.handleCommand(asList(ACTIVITY1NAME.substring(0, 1),
	// TIME1.toString()));
	// Collection<Booking> results = services.getBookingsStore().retrieveAll();
	// assertEquals(1, results.size());
	// assertEquals(ACTIVITY1, get(results, 0).getActivity());
	// assertEquals(TIME1, get(results, 0).getStarttime());
	// assertFalse(get(results, 0).hasEndtime());
	// }
	//
	// @Test
	// public void testAddBookingThreeParamClean() {
	// testee.handleCommand(asList(ACTIVITY1NAME, TIME1.toString(),
	// TIME2.toString()));
	// Collection<Booking> results = services.getBookingsStore().retrieveAll();
	// assertEquals(1, results.size());
	// assertEquals(ACTIVITY1, get(results, 0).getActivity());
	// assertEquals(TIME1, get(results, 0).getStarttime());
	// assertEquals(TIME2, get(results, 0).getEndtime());
	// }
	//
	// @Test(expected = IllegalStateException.class)
	// public void testAddBookingNoParam() {
	// testee.handleCommand(emptyList());
	// }
	//
	// @Test(expected = IllegalStateException.class)
	// public void testAddBookingOneParam() {
	// testee.handleCommand(asList(ACTIVITY1NAME));
	// }
	//
	// @Test(expected = IllegalStateException.class)
	// public void testAddBookingTwoParamWrongActivity() {
	// testee.handleCommand(asList(ACTIVITY1NAME + ACTIVITY1NUMBER,
	// TIME1.toString()));
	// }
	//
	// @Test(expected = DateTimeParseException.class)
	// public void testAddBookingTwoParamEmptyTime() {
	// testee.handleCommand(asList(ACTIVITY1NAME, EMPTY));
	// }
	//
	// @Test(expected = DateTimeParseException.class)
	// public void testAddBookingTwoParamWrongTime() {
	// testee.handleCommand(asList(ACTIVITY1NAME, ACTIVITY1NUMBER));
	// }
	//
	// @Test(expected = DateTimeParseException.class)
	// public void testAddBookingThreeParamWrongTime() {
	// testee.handleCommand(asList(ACTIVITY1NAME, TIME1.toString(),
	// ACTIVITY1NUMBER));
	// }
	//
	// @Test(expected = IllegalStateException.class)
	// public void testAddBookingThreeParamWrongTimeSequence() {
	// testee.handleCommand(asList(ACTIVITY1NAME, TIME2.toString(),
	// TIME1.toString()));
	// }
}
