/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.ActivityModel;

/**
 * Service to manage activities
 */
public interface ActivityService {
    /**
     * @return All known activities, never null
     */
    ActivityModel getActivityModel();

    /**
     * Add a new activity
     *
     * @param name
     *            Name of the activity
     * @param id
     *            Booking number of the activity
     */
    void addLineActivity(String name, String id);

    /**
     * Add a new activity
     *
     * @param name
     *            Name of the activity
     * @param id
     *            Booking number of the activity
     */
    void addProjectActivity(String name, String id);

    /**
     * Remove an activity
     *
     * @param activity
     *            Activity to remove
     */
    void removeActivity(Activity activity);
}
