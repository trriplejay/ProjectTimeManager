package de.lgblaumeiser.ptm.datamanager.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Before;
import org.junit.Test;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.ActivityModel;

public class ActivityServiceTest {
    @NonNull
    private static final String ACTIVITYNAME1 = "a";
    @NonNull
    private static final String ACTIVITYNAME2 = "b";
    @NonNull
    private static final String BOOKINGNUMBER1 = "d";
    @NonNull
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
	assertEquals(2, testee.getActivities().size());
	Iterator<Activity> actIter = testee.getActivities().iterator();
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

    @SuppressWarnings("null")
    @Test
    public void testRemoveActivity() {
	testee.addLineActivity(ACTIVITYNAME1, BOOKINGNUMBER1);
	testee.addProjectActivity(ACTIVITYNAME2, BOOKINGNUMBER2);
	Activity toRemove = testee.getActivities().iterator().next();
	testee.removeActivity(toRemove);
	assertEquals(1, testee.getActivities().size());
	Activity remaining = testee.getActivities().iterator().next();
	if (toRemove.getActivityName().equals(ACTIVITYNAME1)) {
	    assertEquals(ACTIVITYNAME2, remaining.getActivityName());
	} else {
	    assertEquals(ACTIVITYNAME1, remaining.getActivityName());
	}
    }
}
