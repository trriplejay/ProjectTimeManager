package com.bosch.zeitverwaltung.configuration;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ConfigurationTest {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.bosch.zeitverwaltung.configuration");

		suite.addTestSuite(KonfigurationsDatenTest.class);

		return suite;
	}

}
