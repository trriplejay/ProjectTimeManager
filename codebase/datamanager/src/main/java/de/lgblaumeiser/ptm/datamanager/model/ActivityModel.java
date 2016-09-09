/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Set;

/**
 * Class to store all activities in a data structure
 */
public class ActivityModel {
    public static final String ACTIVITY_MODEL_ID = "activities";

    Set<Activity> activities = newHashSet();

    /**
     * @param activity
     *            New activity to add
     */
    public void addActivity(final Activity activity) {
	checkNotNull(activity);
	activities.add(activity);
    }

    /**
     * @param activity
     *            Activity to remove
     */
    public void removeActivity(final Activity activity) {
	checkNotNull(activity);
	activities.remove(activity);
    }

    /**
     * @return All stored activities in an unmodifiable list, may be empty,
     *         never null
     */
    public Collection<Activity> getActivities() {
	return unmodifiableCollection(activities);
    }

    /**
     * Validate the activities, i.e., if booking number is equal, type is the
     * same
     */
    public void validate() {
	for (Activity currentOuter : activities) {
	    for (Activity currentInner : activities) {
		if (!currentOuter.equals(currentInner)) {
		    if (currentOuter.getBookingNumber().equals(currentInner.getBookingNumber())) {
			checkState(currentOuter.isProjectActivity() == currentInner.isProjectActivity());
		    }
		}
	    }
	}
    }

    /**
     * @return An unique identifier for the class
     */
    public String getModelId() {
	return ACTIVITY_MODEL_ID;
    }
}
