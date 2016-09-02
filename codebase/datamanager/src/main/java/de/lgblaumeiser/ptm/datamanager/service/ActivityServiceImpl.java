/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import org.eclipse.jdt.annotation.NonNull;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.ActivityModel;

/**
 * Implementation of Activity Service
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityModel activityModel;

    @SuppressWarnings("null")
    @Override
    public @NonNull ActivityModel getActivityModel() {
	return activityModel;
    }

    @Override
    public void addLineActivity(@NonNull final String name, @NonNull final String id) {
	addActivity(Activity.newLineActivity(name, id));
    }

    @Override
    public void addProjectActivity(@NonNull final String name, @NonNull final String id) {
	addActivity(Activity.newProjectActivity(name, id));
    }

    private void addActivity(@NonNull final Activity newActivity) {
	activityModel.addActivity(newActivity);
	try {
	    activityModel.validate();
	} catch (IllegalStateException e) {
	    activityModel.removeActivity(newActivity);
	    throw e;
	}
    }

    @Override
    public void removeActivity(@NonNull final Activity activity) {
	activityModel.removeActivity(activity);
    }

    public void setActivityStore(final ActivityModel activityStore) {
	this.activityModel = activityStore;
    }
}
