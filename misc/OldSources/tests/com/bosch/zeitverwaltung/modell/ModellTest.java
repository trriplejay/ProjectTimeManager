package com.bosch.zeitverwaltung.modell;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ModellTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.bosch.zeitverwaltung.modell");

		suite.addTestSuite(AktivitaetenVerwaltungTest.class);
		suite.addTestSuite(TagesBuchungenTest.class);

		return suite;
	}

}
