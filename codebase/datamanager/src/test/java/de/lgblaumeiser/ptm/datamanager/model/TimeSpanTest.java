/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;

import org.junit.Test;

/**
 * Test class for the class TimeSpan
 */
public class TimeSpanTest {
    private static final LocalTime TIME1 = LocalTime.of(12, 34);
    private static final LocalTime TIME2 = LocalTime.of(13, 57);
    private static final LocalTime TIME3 = LocalTime.of(14, 57);
    private static final LocalTime TIME4 = LocalTime.of(15, 47);

    /**
     * Positive test method for newTimeSpan
     */
    @Test
    public final void testNewTimeSpanPositive() {
	TimeSpan newTimeSpan = TimeSpan.newTimeSpan(TIME1, TIME2);
	assertEquals(TIME1, newTimeSpan.getStarttime());
	assertEquals(TIME2, newTimeSpan.getEndtime());
    }

    /**
     * Negative test method for newTimeSpan with earlier end time
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNewTimeSpanWrongOrder() {
	TimeSpan.newTimeSpan(TIME2, TIME1);
    }

    /**
     * Negative test method for newTimeSpan with same time
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNewTimeSpanSameTime() {
	TimeSpan.newTimeSpan(TIME1, TIME1);
    }

    /**
     * Equals and HashCode Test
     */
    @Test
    public final void testEqualsHash() {
	TimeSpan timeSpan1 = TimeSpan.newTimeSpan(TIME1, TIME2);
	TimeSpan timeSpan2 = TimeSpan.newTimeSpan(TIME3, TIME4);
	TimeSpan timeSpan3 = TimeSpan.newTimeSpan(TIME1, TIME2);
	TimeSpan timeSpan4 = TimeSpan.newTimeSpan(TIME2, TIME4);

	assertTrue(timeSpan1.equals(timeSpan3));
	assertTrue(timeSpan1.hashCode() == timeSpan3.hashCode());
	assertFalse(timeSpan1.equals(timeSpan2));
	assertFalse(timeSpan1.equals(timeSpan4));
	assertFalse(timeSpan2.equals(timeSpan4));
	assertFalse(timeSpan1.equals(this));
    }
}
