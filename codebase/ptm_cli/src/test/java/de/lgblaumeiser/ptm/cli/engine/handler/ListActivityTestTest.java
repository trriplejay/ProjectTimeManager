/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ListActivityTestTest extends AbstractHandlerTest {
    private static final String LIST_ACTIVITY_COMMAND = "list_activities";

	@Test
	public void testListActivity() {
        commandline.runCommand(LIST_ACTIVITY_COMMAND);
		assertTrue(logger.logMessages.toString().contains("| Activity | Number | Activity Id |"));
	}
}
