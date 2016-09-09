/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

/**
 * Tests for the activity class
 */
public class ActivityTest {
    private final static String LINE_ACTIVITY_1_1 = "LineActivityId11";
    private final static String LINE_ACTIVITY_1_2 = "LineActivityId12";
    private final static String LINE_BOOKING_1 = "L1";
    private final static String PROJECT_ACTIVITY_1_1 = "ProjectActivityId11";
    private final static String PROJECT_BOOKING_1 = "P1";

    /**
     * Positive test method for newLineActivity with activity id
     */
    @Test
    public final void testNewLineActivityPositive() {
	Activity newActivity = Activity.newLineActivity(LINE_ACTIVITY_1_1, LINE_BOOKING_1);
	assertEquals(LINE_ACTIVITY_1_1, newActivity.getActivityName());
	assertEquals(LINE_BOOKING_1, newActivity.getBookingNumber());
	assertFalse(newActivity.isProjectActivity());
    }

    /**
     * Positive test method for newProjectActivity with activity id
     */
    @Test
    public final void testNewProjectActivityPositive() {
	Activity newActivity = Activity.newProjectActivity(PROJECT_ACTIVITY_1_1, PROJECT_BOOKING_1);
	assertEquals(PROJECT_ACTIVITY_1_1, newActivity.getActivityName());
	assertEquals(PROJECT_BOOKING_1, newActivity.getBookingNumber());
	assertTrue(newActivity.isProjectActivity());
    }

    @Test(expected = IllegalStateException.class)
    public final void testLineWithBlankName() {
	Activity.newLineActivity(StringUtils.EMPTY, LINE_BOOKING_1);
    }

    @Test(expected = IllegalStateException.class)
    public final void testProjectWithBlankName() {
	Activity.newProjectActivity(StringUtils.EMPTY, PROJECT_BOOKING_1);
    }

    @Test(expected = IllegalStateException.class)
    public final void testLineWithBlankNumber() {
	Activity.newLineActivity(LINE_ACTIVITY_1_1, StringUtils.EMPTY);
    }

    @Test(expected = IllegalStateException.class)
    public final void testProjectWithBlankNumber() {
	Activity.newProjectActivity(PROJECT_ACTIVITY_1_1, StringUtils.EMPTY);
    }

    /**
     * Test for equals and hashcode
     */
    @Test
    public final void testEquals() {
	Activity newActivity1 = Activity.newLineActivity(LINE_ACTIVITY_1_1, LINE_BOOKING_1);
	Activity newActivity2 = Activity.newLineActivity(LINE_ACTIVITY_1_2, LINE_BOOKING_1);
	Activity newActivity3 = Activity.newProjectActivity(PROJECT_ACTIVITY_1_1, PROJECT_BOOKING_1);
	Activity newActivity4 = Activity.newLineActivity(LINE_ACTIVITY_1_1, LINE_BOOKING_1);

	assertTrue(newActivity1.equals(newActivity4));
	assertTrue(newActivity1.hashCode() == newActivity4.hashCode());
	assertFalse(newActivity1.equals(newActivity2));
	assertFalse(newActivity1.equals(newActivity3));
    }
}
