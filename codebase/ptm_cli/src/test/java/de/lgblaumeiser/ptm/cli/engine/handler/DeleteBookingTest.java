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

import com.beust.jcommander.ParameterException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class DeleteBookingTest extends AbstractHandlerTest {
    private static final String DELETE_BOOKING_COMMAND = "delete_booking";

	@Test
	public void testDeleteBookingClean() {
        commandline.runCommand(DELETE_BOOKING_COMMAND, "-b", "1");
        assertEquals("/bookings/id/1", restutils.apiNameGiven);
	}

	@Test(expected = ParameterException.class)
	public void testDeleteBookingNoParam() {
        commandline.runCommand(DELETE_BOOKING_COMMAND);
	}

	@Test(expected = ParameterException.class)
	public void testDeleteBookingEmptyParam() {
        commandline.runCommand(DELETE_BOOKING_COMMAND, "-b");
	}
}
