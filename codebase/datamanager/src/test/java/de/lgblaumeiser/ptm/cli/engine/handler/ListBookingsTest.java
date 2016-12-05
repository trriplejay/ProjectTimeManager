package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ListBookingsTest extends AbstractHandlerTest {
	private ListBookings testee = new ListBookings();

	@Test
	public void test() {
		services.getBookingService().endBooking(
				services.getStateStore().getCurrentDay(),
				services.getBookingService().addBooking(services.getStateStore().getCurrentDay(), ACTIVITY1, TIME1),
				TIME2);
		testee.handleCommand(emptyList());
		assertTrue(logger.logMessages.toString().contains(ACTIVITY1NAME));
		assertTrue(logger.logMessages.toString().contains(ACTIVITY1NUMBER));
		assertTrue(logger.logMessages.toString().contains(TIME1.toString()));
		assertTrue(logger.logMessages.toString().contains(TIME2.toString()));
	}
}
