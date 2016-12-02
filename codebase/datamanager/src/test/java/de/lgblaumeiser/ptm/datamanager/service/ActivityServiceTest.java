/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.ActivityModel;

public class ActivityServiceTest {
	private static final String ACTIVITYNAME1 = "aaa";
	private static final String ACTIVITYNAME2 = "bbb";
	private static final String BOOKINGNUMBER1 = "d";
	private static final String BOOKINGNUMBER2 = "e";

	ActivityService testee;

	@Before
	public void setup() {
		ActivityServiceImpl testeeImpl = new ActivityServiceImpl();
		testeeImpl.setActivityStore(new ActivityModel());
		testee = testeeImpl;
	}

	@Test
	public void testAddActivity() {
		testee.addLineActivity(ACTIVITYNAME1, BOOKINGNUMBER1);
		testee.addProjectActivity(ACTIVITYNAME2, BOOKINGNUMBER2);
		assertEquals(2, testee.getActivityModel().getActivities().size());
		Iterator<Activity> actIter = testee.getActivityModel().getActivities().iterator();
		Activity act1 = actIter.next();
		Activity act2 = actIter.next();
		if (ACTIVITYNAME1.equals(act1.getActivityName())) {
			check(act1, act2);
		} else {
			check(act2, act1);
		}
	}

	private void check(final Activity act1, final Activity act2) {
		assertFalse(act1.isProjectActivity());
		assertTrue(act2.isProjectActivity());
		assertEquals(BOOKINGNUMBER1, act1.getBookingNumber());
		assertEquals(BOOKINGNUMBER2, act2.getBookingNumber());
	}
	
	@Test
	public void testGetActivityByAbreviatedName() {
		testee.addLineActivity(ACTIVITYNAME1, BOOKINGNUMBER1);
		testee.addProjectActivity(ACTIVITYNAME2, BOOKINGNUMBER2);
		assertEquals(2, testee.getActivityModel().getActivities().size());
		assertNotNull(testee.getActivityByAbbreviatedName(ACTIVITYNAME1));
		assertNotNull(testee.getActivityByAbbreviatedName(ACTIVITYNAME2.substring(0, 1)));
	}

	@Test(expected = IllegalStateException.class)
	public void testGetActivityByNonExistingName() {
		testee.addLineActivity(ACTIVITYNAME1, BOOKINGNUMBER1);
		testee.addProjectActivity(ACTIVITYNAME2, BOOKINGNUMBER2);
		assertEquals(2, testee.getActivityModel().getActivities().size());
		testee.getActivityByAbbreviatedName(BOOKINGNUMBER1);
	}
}
