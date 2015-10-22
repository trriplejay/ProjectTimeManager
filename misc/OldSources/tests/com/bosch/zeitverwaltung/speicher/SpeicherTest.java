package com.bosch.zeitverwaltung.speicher;

import junit.framework.Test;
import junit.framework.TestSuite;

public class SpeicherTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.bosch.zeitverwaltung.speicher");

		suite.addTestSuite(SpeicherBuchungenTest.class);

		return suite;
	}

}
