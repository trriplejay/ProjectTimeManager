/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.stream.Collectors;

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
	public Activity getActivityByAbbreviatedName(final String name) {
		checkNotNull(name);
		List<Activity> results = activityModel.getActivities().stream()
				.filter((activity) -> activity.getActivityName().toUpperCase().startsWith(name.toUpperCase()))
				.collect(Collectors.toList());
		checkState(results.size() == 1);
		return results.get(0);
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

	public void setActivityStore(final ActivityModel activityStore) {
		this.activityModel = activityStore;
	}
}
