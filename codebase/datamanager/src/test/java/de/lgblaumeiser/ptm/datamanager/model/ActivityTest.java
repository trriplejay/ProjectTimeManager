/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

/**
 * Tests for the activity class
 */
public class ActivityTest {
    @NonNull
    private final static String LINE_ACTIVITY_1_1 = "LineActivityId11";
    @NonNull
    private final static String LINE_ACTIVITY_1_2 = "LineActivityId12";
    @NonNull
    private final static String LINE_BOOKING_1 = "L1";
    @NonNull
    private final static String LINE_BOOKING_2 = "L2";
    @NonNull
    private final static String PROJECT_ACTIVITY_1_1 = "ProjectActivityId11";
    @NonNull
    private final static String PROJECT_ACTIVITY_1_2 = "ProjectActivityId12";
    @NonNull
    private final static String PROJECT_BOOKING_1 = "P1";
    @NonNull
    private final static String PROJECT_BOOKING_2 = "P2";

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

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public final void testLineWithBlankName() {
	Activity.newLineActivity(StringUtils.EMPTY, LINE_BOOKING_1);
    }

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public final void testProjectWithBlankName() {
	Activity.newProjectActivity(StringUtils.EMPTY, PROJECT_BOOKING_1);
    }

    @SuppressWarnings("null")
    @Test(expected = IllegalStateException.class)
    public final void testLineWithBlankNumber() {
	Activity.newLineActivity(LINE_ACTIVITY_1_1, StringUtils.EMPTY);
    }

    @SuppressWarnings("null")
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
