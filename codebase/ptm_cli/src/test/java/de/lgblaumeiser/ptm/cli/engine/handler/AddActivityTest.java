/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.ParameterException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddActivityTest extends AbstractHandlerTest {
    private static final String ADD_ACTIVITY_COMMAND = "add_activity";

	@Test
	public void testAddActivityTwoParamClean() {
	    commandline.runCommand(ADD_ACTIVITY_COMMAND, "-n", ACTIVITY1NAME, "-i", ACTIVITY1NUMBER);
	    assertEquals("/activities", restutils.apiNameGiven);
	    assertEquals(ACTIVITY1NAME, restutils.bodyDataGiven.get("activityName"));
	    assertEquals(ACTIVITY1NUMBER, restutils.bodyDataGiven.get("bookingNumber"));
	    assertEquals("false", restutils.bodyDataGiven.get("hidden"));
	    assertEquals(3, restutils.bodyDataGiven.size());
	}

	@Test(expected = ParameterException.class)
	public void testAddActivityTwoParamFirstNull() {
		commandline.runCommand(ADD_ACTIVITY_COMMAND, "-n", "-i", ACTIVITY1NUMBER);
	}

	@Test(expected = ParameterException.class)
	public void testAddActivityTwoParamSecondNull() {
		commandline.runCommand(ADD_ACTIVITY_COMMAND, "-n", ACTIVITY1NAME, "-i");
	}

	@Test(expected = ParameterException.class)
	public void testAddActivityOneParam() {
		commandline.runCommand(ADD_ACTIVITY_COMMAND, "-n", ACTIVITY1NAME);
	}

	@Test(expected = ParameterException.class)
	public void testAddActivityNoParam() {
		commandline.runCommand(ADD_ACTIVITY_COMMAND);
	}
}
