/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Before;
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
    private final static String LINE_CATEGORY_1 = "LineCategoryId1";
    @NonNull
    private final static String LINE_BOOKING_1 = "L1";
    @NonNull
    private final static String LINE_CATEGORY_2 = "LineCategoryId2";
    @NonNull
    private final static String LINE_BOOKING_2 = "L2";
    @NonNull
    private final static String PROJECT_ACTIVITY_1_1 = "ProjectActivityId11";
    @NonNull
    private final static String PROJECT_ACTIVITY_1_2 = "ProjectActivityId12";
    @NonNull
    private final static String PROJECT_CATEGORY_1 = "ProjectCategoryId1";
    @NonNull
    private final static String PROJECT_BOOKING_1 = "P1";
    @NonNull
    private final static String PROJECT_CATEGORY_2 = "ProjectCategoryId2";
    @NonNull
    private final static String PROJECT_BOOKING_2 = "P2";

    @Before
    public void before() {
	Activity.reset();
    }

    /**
     * Positive test method for newLineActivity with activity id
     */
    @Test
    public final void testNewLineActivityPositive() {
	Activity newActivity = Activity.newLineActivity(LINE_ACTIVITY_1_1, LINE_CATEGORY_1, LINE_BOOKING_1);
	assertEquals(LINE_ACTIVITY_1_1, newActivity.getActivityId());
	assertEquals(LINE_CATEGORY_1, newActivity.getCategoryId());
	assertEquals(LINE_BOOKING_1, newActivity.getBookingNumber());
	assertFalse(newActivity.isProjectActivity());
    }

    /**
     * Positive test method for newLineActivity without activity id
     */
    @Test
    public final void testNewLineCategoryPositive() {
	Activity newActivity = Activity.newLineActivity(LINE_CATEGORY_2, LINE_BOOKING_2);
	assertEquals(LINE_CATEGORY_2, newActivity.getActivityId());
	assertEquals(LINE_CATEGORY_2, newActivity.getCategoryId());
	assertEquals(LINE_BOOKING_2, newActivity.getBookingNumber());
	assertFalse(newActivity.isProjectActivity());
    }

    /**
     * Positive test method for newProjectActivity with activity id
     */
    @Test
    public final void testNewProjectActivityPositive() {
	Activity newActivity = Activity.newProjectActivity(PROJECT_ACTIVITY_1_1, PROJECT_CATEGORY_1, PROJECT_BOOKING_1);
	assertEquals(PROJECT_ACTIVITY_1_1, newActivity.getActivityId());
	assertEquals(PROJECT_CATEGORY_1, newActivity.getCategoryId());
	assertEquals(PROJECT_BOOKING_1, newActivity.getBookingNumber());
	assertTrue(newActivity.isProjectActivity());
    }

    /**
     * Positive test method for newProjectActivity without activity id
     */
    @Test
    public final void testNewProjectPositive() {
	Activity newActivity = Activity.newProjectActivity(PROJECT_CATEGORY_2, PROJECT_BOOKING_2);
	assertEquals(PROJECT_CATEGORY_2, newActivity.getActivityId());
	assertEquals(PROJECT_CATEGORY_2, newActivity.getCategoryId());
	assertEquals(PROJECT_BOOKING_2, newActivity.getBookingNumber());
	assertTrue(newActivity.isProjectActivity());
    }

    /**
     * Positive test method for newLineActivity with two activity ids
     */
    @Test
    public final void testNewLinePositiveTwoActivities() {
	Activity newActivity1 = Activity.newLineActivity(LINE_ACTIVITY_1_1, LINE_CATEGORY_1, LINE_BOOKING_1);
	Activity newActivity2 = Activity.newLineActivity(LINE_ACTIVITY_1_2, LINE_CATEGORY_1, LINE_BOOKING_1);
	assertEquals(LINE_ACTIVITY_1_1, newActivity1.getActivityId());
	assertEquals(LINE_ACTIVITY_1_2, newActivity2.getActivityId());
	assertEquals(LINE_CATEGORY_1, newActivity1.getCategoryId());
	assertEquals(LINE_CATEGORY_1, newActivity2.getCategoryId());
	assertEquals(LINE_BOOKING_1, newActivity1.getBookingNumber());
	assertEquals(LINE_BOOKING_1, newActivity2.getBookingNumber());
	assertFalse(newActivity1.isProjectActivity());
	assertFalse(newActivity2.isProjectActivity());
    }

    /**
     * Positive test method for newProjectActivity with two activity ids
     */
    @Test
    public final void testNewProjectPositiveTwoActivities() {
	Activity newActivity1 = Activity.newLineActivity(PROJECT_ACTIVITY_1_1, PROJECT_CATEGORY_1, PROJECT_BOOKING_1);
	Activity newActivity2 = Activity.newLineActivity(PROJECT_ACTIVITY_1_2, PROJECT_CATEGORY_1, PROJECT_BOOKING_1);
	assertEquals(PROJECT_ACTIVITY_1_1, newActivity1.getActivityId());
	assertEquals(PROJECT_ACTIVITY_1_2, newActivity2.getActivityId());
	assertEquals(PROJECT_CATEGORY_1, newActivity1.getCategoryId());
	assertEquals(PROJECT_CATEGORY_1, newActivity2.getCategoryId());
	assertEquals(PROJECT_BOOKING_1, newActivity1.getBookingNumber());
	assertEquals(PROJECT_BOOKING_1, newActivity2.getBookingNumber());
	assertFalse(newActivity1.isProjectActivity());
	assertFalse(newActivity2.isProjectActivity());
    }

    /**
     * Negative test method for a line activity with a known booking number but
     * other category
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNewLineWithDoubleBookingNumber() {
	Activity.newLineActivity(LINE_CATEGORY_1, LINE_BOOKING_1);
	Activity.newLineActivity(LINE_CATEGORY_2, LINE_BOOKING_1);
    }

    /**
     * Negative test method for a project activity with a known booking number
     * but other category
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNewProjectWithDoubleBookingNumber() {
	Activity.newProjectActivity(PROJECT_CATEGORY_1, PROJECT_BOOKING_1);
	Activity.newProjectActivity(PROJECT_CATEGORY_2, PROJECT_BOOKING_1);
    }

    /**
     * Negative test method for a line activity with an activity although type
     * does not take activities
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNewLineWithInconsistentActivityIdDefinition() {
	Activity.newLineActivity(LINE_CATEGORY_2, LINE_BOOKING_2);
	Activity.newLineActivity(LINE_ACTIVITY_1_1, LINE_CATEGORY_2, LINE_BOOKING_2);
    }

    /**
     * Negative test method for a project activity with an activity although
     * type does not take activities
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNewProjectWithInconsistentActivityIdDefinition() {
	Activity.newProjectActivity(PROJECT_CATEGORY_2, PROJECT_BOOKING_2);
	Activity.newProjectActivity(PROJECT_ACTIVITY_1_1, PROJECT_CATEGORY_2, PROJECT_BOOKING_2);
    }

    /**
     * Negative test method for a line activity without an activity although
     * type needs activities
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNewLineWithInconsistentActivityIdDefinition2() {
	Activity.newLineActivity(LINE_ACTIVITY_1_1, LINE_CATEGORY_2, LINE_BOOKING_2);
	Activity.newLineActivity(LINE_CATEGORY_2, LINE_BOOKING_2);
    }

    /**
     * Negative test method for a project activity without an activity although
     * type needs activities
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testNewProjectWithInconsistentActivityIdDefinition2() {
	Activity.newProjectActivity(PROJECT_ACTIVITY_1_1, PROJECT_CATEGORY_2, PROJECT_BOOKING_2);
	Activity.newProjectActivity(PROJECT_CATEGORY_2, PROJECT_BOOKING_2);
    }

    /**
     * Test for equals and hashcode
     */
    @Test
    public final void testEquals() {
	Activity newActivity1 = Activity.newLineActivity(LINE_ACTIVITY_1_1, LINE_CATEGORY_1, LINE_BOOKING_1);
	Activity newActivity2 = Activity.newLineActivity(LINE_ACTIVITY_1_2, LINE_CATEGORY_1, LINE_BOOKING_1);
	Activity newActivity3 = Activity.newProjectActivity(PROJECT_ACTIVITY_1_1, PROJECT_CATEGORY_1,
		PROJECT_BOOKING_1);
	Activity newActivity4 = Activity.newLineActivity(LINE_ACTIVITY_1_1, LINE_CATEGORY_1, LINE_BOOKING_1);

	assertTrue(newActivity1.equals(newActivity4));
	assertTrue(newActivity1.hashCode() == newActivity4.hashCode());
	assertFalse(newActivity1.equals(newActivity2));
	assertFalse(newActivity1.equals(newActivity3));
    }
}
