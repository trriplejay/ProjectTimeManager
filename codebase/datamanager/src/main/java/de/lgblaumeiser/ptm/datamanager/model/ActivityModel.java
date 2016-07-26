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
    Set<@NonNull Activity> activities = newHashSet();

    public static ActivityModel createActivityModel() {
	return new ActivityModel();
    }

    private ActivityModel() {
	// Prevent creation from outside
    }

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
}
