package com.bosch.zeitverwaltung.services;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

public class ClasspathSucheTest extends TestCase {
	public final void testClasspathSuche() {
		try {
			ClasspathSuche testee = new ClasspathSuche(
					"zeitverwaltungfactory.xml");
			assertTrue(testee.hasFiles());
			assertNotNull(testee.nextInputStream());
			assertTrue(testee.hasFiles());
			assertNotNull(testee.nextInputStream());
			assertTrue(testee.hasFiles());
			assertNotNull(testee.nextInputStream());
			assertTrue(testee.hasFiles());
			assertNotNull(testee.nextInputStream());
			assertFalse(testee.hasFiles());
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

		try {
			ClasspathSuche testee = new ClasspathSuche("TestCase.class");
			assertTrue(testee.hasFiles());
			InputStream ips = testee.nextInputStream();
			assertNotNull(ips);
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
}
