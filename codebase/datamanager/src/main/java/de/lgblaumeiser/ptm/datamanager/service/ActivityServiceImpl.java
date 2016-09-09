/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import static com.google.common.base.Preconditions.checkNotNull;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.ActivityModel;

/**
 * Implementation of Activity Service
 */
public class ActivityServiceImpl implements ActivityService {
    private ActivityModel activityModel;

    @Override
    public ActivityModel getActivityModel() {
	return activityModel;
    }

    @Override
    public void addLineActivity(final String name, final String id) {
	checkNotNull(name);
	checkNotNull(id);
	addActivity(Activity.newLineActivity(name, id));
    }

    @Override
    public void addProjectActivity(final String name, final String id) {
	checkNotNull(name);
	checkNotNull(id);
	addActivity(Activity.newProjectActivity(name, id));
    }

    private void addActivity(final Activity newActivity) {
	activityModel.addActivity(newActivity);
	try {
	    activityModel.validate();
	} catch (IllegalStateException e) {
	    activityModel.removeActivity(newActivity);
	    throw e;
	}
    }

    @Override
    public void removeActivity(final Activity activity) {
	checkNotNull(activity);
	activityModel.removeActivity(activity);
    }

    public void setActivityStore(final ActivityModel activityStore) {
	this.activityModel = activityStore;
    }
}
