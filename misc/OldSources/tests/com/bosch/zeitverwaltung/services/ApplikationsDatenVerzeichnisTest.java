package com.bosch.zeitverwaltung.services;

import java.io.File;

import junit.framework.TestCase;

public class ApplikationsDatenVerzeichnisTest extends TestCase {

	public final void testGetAppdataVerzeichnis() {
		String userHome = System.getProperty("user.home");
		File dir = new File(userHome);
		assertTrue(dir.exists());
		File appdir = new File(dir, "Application Data");
		if (!dir.exists()) {
			appdir = new File(dir, "Anwendungsdaten");
		}
		assertTrue(dir.exists());

		boolean delete = false;
		File datadir = new File(appdir, "BoschZeitverwaltung");
		if (datadir.exists()) {
			delete = true;
		}
		File vgldir = new ApplikationsDatenVerzeichnis()
				.getAppdataVerzeichnis();

		assertTrue(datadir.exists());
		assertEquals(datadir, vgldir);

		if (delete) {
			datadir.delete();
		}
	}

}
