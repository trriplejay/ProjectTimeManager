/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.Collections.unmodifiableCollection;

import java.util.Collection;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Class to store all activities in a data structure
 */
public class ActivityModel {
    @NonNull
    public static final String ACTIVITY_MODEL_ID = "activities";

    Set<@NonNull Activity> activities = newHashSet();

    public void addActivity(@NonNull final Activity activity) {
	activities.add(activity);
    }

    public void removeActivity(@NonNull final Activity activity) {
	activities.remove(activity);
    }

    @SuppressWarnings("null")
    @NonNull
    public Collection<@NonNull Activity> getActivities() {
	return unmodifiableCollection(activities);
    }

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

    @NonNull
    public String getModelId() {
	return ACTIVITY_MODEL_ID;
    }
}
