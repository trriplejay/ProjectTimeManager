/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static com.google.common.collect.Iterables.get;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;

import com.beust.jcommander.ParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Booking;

public class AddBookingTest extends AbstractHandlerTest {
    private static final String ADD_BOOKING_COMMAND = "add_booking";

	@Test
	public void testAddBookingThreeParam() {
        commandline.runCommand(ADD_BOOKING_COMMAND, "-a", "1", "-u", USER, "-s", TIME1.toString());
        assertEquals("/bookings/day/" + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE), restutils.apiNameGiven);
        assertEquals("1", restutils.bodyDataGiven.get("activityId"));
        assertEquals(USER, restutils.bodyDataGiven.get("user"));
        assertEquals(TIME1.toString(), restutils.bodyDataGiven.get("starttime"));
        assertEquals(StringUtils.EMPTY, restutils.bodyDataGiven.get("comment"));
        assertEquals(4, restutils.bodyDataGiven.size());
	}

	@Test
	public void testAddBookingFourParamEndtime() {
        commandline.runCommand(ADD_BOOKING_COMMAND, "-a", "1", "-u", USER, "-s", TIME1.toString(), "-e", TIME2.toString());
		assertEquals("1", restutils.bodyDataGiven.get("activityId"));
        assertEquals(USER, restutils.bodyDataGiven.get("user"));
        assertEquals(TIME1.toString(), restutils.bodyDataGiven.get("starttime"));
        assertEquals(TIME2.toString(), restutils.bodyDataGiven.get("endtime"));
        assertEquals(StringUtils.EMPTY, restutils.bodyDataGiven.get("comment"));
        assertEquals(5, restutils.bodyDataGiven.size());
	}

    @Test
    public void testAddBookingFourParamComment() {
        commandline.runCommand(ADD_BOOKING_COMMAND, "-a", "1", "-u", USER, "-s", TIME1.toString(), "-c", COMMENT);
        assertEquals("/bookings/day/" + LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE), restutils.apiNameGiven);
        assertEquals("1", restutils.bodyDataGiven.get("activityId"));
        assertEquals(USER, restutils.bodyDataGiven.get("user"));
        assertEquals(TIME1.toString(), restutils.bodyDataGiven.get("starttime"));
        assertEquals(COMMENT, restutils.bodyDataGiven.get("comment"));
        assertEquals(4, restutils.bodyDataGiven.size());
    }

	@Test(expected = ParameterException.class)
	public void testAddBookingNoParam() {
        commandline.runCommand(ADD_BOOKING_COMMAND);
	}

	@Test(expected = ParameterException.class)
	public void testAddBookingOneParam() {
        commandline.runCommand(ADD_BOOKING_COMMAND, "-a", "1");
	}

	@Test(expected = ParameterException.class)
	public void testAddBookingTwoParam() {
        commandline.runCommand(ADD_BOOKING_COMMAND, "-a", "1", "-u", USER);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBookingThreeParamWrongActivity() {
        commandline.runCommand(ADD_BOOKING_COMMAND, "-a", "3", "-u", USER, "-s", TIME1.toString());
	}

	@Test(expected = DateTimeParseException.class)
	public void testAddBookingTwoParamWrongTime() {
        commandline.runCommand(ADD_BOOKING_COMMAND, "-a", "1", "-u", USER, "-s", ACTIVITY1NUMBER);
	}

	@Test(expected = DateTimeParseException.class)
	public void testAddBookingThreeParamWrongTime() {
        commandline.runCommand(ADD_BOOKING_COMMAND, "-a", "3", "-u", USER, "-s", TIME1.toString(), "-e", ACTIVITY1NUMBER);
	}

	@Test(expected = IllegalStateException.class)
	public void testAddBookingFourParamWrongTimeSequence() {
        commandline.runCommand(ADD_BOOKING_COMMAND, "-a", "3", "-u", USER, "-s", TIME2.toString(), "-e", TIME1.toString());
	}
}
