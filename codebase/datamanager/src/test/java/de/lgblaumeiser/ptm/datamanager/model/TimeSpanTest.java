/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.datamanager.model;

import org.junit.Test;

import java.time.LocalTime;

import static de.lgblaumeiser.ptm.datamanager.model.TimeSpan.newTimeSpan;
import static java.time.LocalTime.of;
import static org.junit.Assert.*;

/**
 * Test class for the class TimeSpan
 */
public class TimeSpanTest {
	private static final LocalTime TIME1 = of(12, 34);
	private static final LocalTime TIME2 = of(13, 57);
	private static final LocalTime TIME3 = of(14, 57);
	private static final LocalTime TIME4 = of(15, 47);

	private static final long DIFF23 = 60;

	/**
	 * Positive test method for newTimeSpan
	 */
	@Test
	public final void testNewTimeSpanPositive() {
		TimeSpan newTimeSpan = newTimeSpan(TIME2, TIME3);
		assertEquals(DIFF23, newTimeSpan.getLengthInMinutes().toMinutes());
	}

	/**
	 * Negative test method for newTimeSpan with earlier end time
	 */
	@Test(expected = IllegalStateException.class)
	public final void testNewTimeSpanWrongOrder() {
		newTimeSpan(TIME2, TIME1);
	}

	/**
	 * Negative test method for newTimeSpan with same time
	 */
	@Test(expected = IllegalStateException.class)
	public final void testNewTimeSpanSameTime() {
		newTimeSpan(TIME1, TIME1);
	}

	/**
	 * Equals and HashCode Test
	 */
	@Test
	public final void testEqualsHash() {
		TimeSpan timeSpan1 = newTimeSpan(TIME1, TIME2);
		TimeSpan timeSpan2 = newTimeSpan(TIME3, TIME4);
		TimeSpan timeSpan3 = newTimeSpan(TIME1, TIME2);
		TimeSpan timeSpan4 = newTimeSpan(TIME2, TIME4);

		assertTrue(timeSpan1.equals(timeSpan3));
		assertTrue(timeSpan1.hashCode() == timeSpan3.hashCode());
		assertFalse(timeSpan1.equals(timeSpan2));
		assertFalse(timeSpan1.equals(timeSpan4));
		assertFalse(timeSpan2.equals(timeSpan4));
		assertFalse(timeSpan1.equals(this));
	}
}
