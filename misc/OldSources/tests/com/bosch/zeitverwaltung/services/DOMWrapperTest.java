package com.bosch.zeitverwaltung.services;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.w3c.dom.Element;

import junit.framework.TestCase;

public class DOMWrapperTest extends TestCase {
	public final void testDOMWrapper() {
		try {
			File tempfile = File.createTempFile("xmltest", "xml");
			DOMWriter writer = new DOMWriter(tempfile, "dummy1");
			Element vater = writer.neuesElement(null, "dummy2");
			Element kind = writer.neuesElement(vater, "dummy3");

			kind.setAttribute("dummy4", "dummy5");

			writer.speichereDatei();

			DOMReader reader = new DOMReader(tempfile);

			Element test = reader.getElement(null, "dummy2");
			assertNotNull(test);
			Collection<Element> list = reader.getElementListe(test, "dummy3");
			assertTrue(list.size() == 1);
			Iterator<Element> iter = list.iterator();
			test = iter.next();
			assertNotNull(test);
			assertEquals("dummy5", test.getAttribute("dummy4"));

			tempfile.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

	}
}
