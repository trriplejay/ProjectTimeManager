/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Test for license command
 */
public class LicenseTest extends AbstractHandlerTest {
	private static final String LICENSE_COMMAND = "licenses";

	@Test
	public void testLicense() {
		commandline.runCommand(LICENSE_COMMAND);
		assertEquals("/services/license", restutils.apiNameGiven);
		assertTrue(logger.logMessages.toString().contains("Apache-2.0"));
		assertTrue(logger.logMessages.toString().contains("MIT"));
	}
}
