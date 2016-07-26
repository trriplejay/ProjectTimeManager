package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.collect.Iterables.getOnlyElement;
import static org.junit.Assert.assertEquals;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

public class ActivityModelTest {
    private final ActivityModel testee = ActivityModel.createActivityModel();

    @NonNull
    private static final String ACTIVITYNAME1 = "a";
    @NonNull
    private static final String ACTIVITYNAME2 = "b";
    @NonNull
    private static final String ACTIVITYNAME3 = "c";
    @NonNull
    private static final String BOOKINGNUMBER1 = "d";
    @NonNull
    private static final String BOOKINGNUMBER2 = "e";
    @NonNull
    private static final Activity ACTIVITY1 = Activity.newLineActivity(ACTIVITYNAME1, BOOKINGNUMBER1);
    @NonNull
    private static final Activity ACTIVITY2 = Activity.newProjectActivity(ACTIVITYNAME2, BOOKINGNUMBER2);
    @NonNull
    private static final Activity ACTIVITY3 = Activity.newProjectActivity(ACTIVITYNAME3, BOOKINGNUMBER1);

    @Test
    public void testAddActivity() {
	testee.addActivity(ACTIVITY1);
	testee.addActivity(ACTIVITY2);
	assertEquals(2, testee.getActivities().size());
    }

    public void testAddActivityTwiceNoIssue() {
	testee.addActivity(ACTIVITY1);
	testee.addActivity(ACTIVITY1);
	assertEquals(1, testee.getActivities().size());
    }

    @Test
    public void testRemoveActivity() {
	testee.addActivity(ACTIVITY1);
	testee.addActivity(ACTIVITY2);
	testee.removeActivity(ACTIVITY1);
	assertEquals(1, testee.getActivities().size());
	assertEquals(ACTIVITY2, getOnlyElement(testee.getActivities()));
    }

    @Test
    public void testValidate() {
	testee.addActivity(ACTIVITY1);
	testee.addActivity(ACTIVITY2);
	testee.validate();
    }

    @Test(expected = IllegalStateException.class)
    public void testValidateNegative() {
	testee.addActivity(ACTIVITY1);
	testee.addActivity(ACTIVITY3);
	testee.validate();
    }
}
