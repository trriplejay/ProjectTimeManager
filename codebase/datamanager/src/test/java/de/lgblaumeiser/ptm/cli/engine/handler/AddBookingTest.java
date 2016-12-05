package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.format.DateTimeParseException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Booking;

public class AddBookingTest extends AbstractHandlerTest {
	private AddBooking testee = new AddBooking();
	
	@Before
	public void before() {
		super.before();
		actModel.addActivity(ACTIVITY1);
	}
	
	@Test
	public void testAddBookingTwoParamClean() {
		testee.handleCommand(asList(ACTIVITY1NAME.substring(0, 1), TIME1.toString()));
		List<Booking> results = services.getStateStore().getCurrentDay().getBookings();
		assertEquals(1, results.size());
		assertEquals(ACTIVITY1, results.get(0).getActivity());
		assertEquals(TIME1, results.get(0).getStarttime());
		assertFalse(results.get(0).hasEndtime());
	}

	@Test
	public void testAddBookingThreeParamClean() {
		testee.handleCommand(asList(ACTIVITY1NAME, TIME1.toString(), TIME2.toString()));
		List<Booking> results = services.getStateStore().getCurrentDay().getBookings();
		assertEquals(1, results.size());
		assertEquals(ACTIVITY1, results.get(0).getActivity());
		assertEquals(TIME1, results.get(0).getStarttime());
		assertEquals(TIME2, results.get(0).getEndtime());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testAddBookingNoParam() {
		testee.handleCommand(emptyList());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testAddBookingOneParam() {
		testee.handleCommand(asList(ACTIVITY1NAME));
	}
	
	@Test(expected = IllegalStateException.class)
	public void testAddBookingTwoParamWrongActivity() {
		testee.handleCommand(asList(ACTIVITY1NAME + ACTIVITY1NUMBER, TIME1.toString()));
	}

	@Test(expected = DateTimeParseException.class)
	public void testAddBookingTwoParamEmptyTime() {
		testee.handleCommand(asList(ACTIVITY1NAME, StringUtils.EMPTY));
	}

	@Test(expected = DateTimeParseException.class)
	public void testAddBookingTwoParamWrongTime() {
		testee.handleCommand(asList(ACTIVITY1NAME, ACTIVITY1NUMBER));
	}

	@Test(expected = DateTimeParseException.class)
	public void testAddBookingThreeParamWrongTime() {
		testee.handleCommand(asList(ACTIVITY1NAME, TIME1.toString(), ACTIVITY1NUMBER));
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBookingThreeParamWrongTimeSequence() {
		testee.handleCommand(asList(ACTIVITY1NAME, TIME2.toString(), TIME1.toString()));	
	}
}
