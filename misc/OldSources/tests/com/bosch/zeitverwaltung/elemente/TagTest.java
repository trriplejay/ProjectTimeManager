package com.bosch.zeitverwaltung.elemente;

import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class TagTest extends TestCase {

	public final void testTag() {
		Tag tag1 = new Tag(new Date());
		Tag tag2 = new Tag(2007, 6, 22);
		Tag tag3 = new Tag(2009, 7, 31);
		Tag tag4 = new Tag(2007, 6, 22);

		Calendar heute = Calendar.getInstance();
		heute.setTime(new Date());

		assertEquals(heute.get(Calendar.YEAR), tag1.getJahr());
		assertEquals(heute.get(Calendar.MONTH) + 1, tag1.getMonat());
		assertEquals(heute.get(Calendar.DAY_OF_MONTH), tag1.getTag());

		assertEquals(2007, tag2.getJahr());
		assertEquals(6, tag2.getMonat());
		assertEquals(22, tag2.getTag());

		assertTrue(tag1.compareTo(tag2) > 0);
		assertTrue(tag1.compareTo(tag3) < 0);
		assertTrue(tag2.compareTo(tag4) == 0);
	}

}
