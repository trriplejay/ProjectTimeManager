/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.Preconditions.checkState;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.MoreObjects;

/**
 * Data structure for representation of an activity. An activity represents a
 * booking number under which work is done. Each work booking is assigned to an
 * activity and there are reports on hours spent on activities. Some activities
 * are considered as project activities and others are not, like vacation times.
 * This property is stored by a flag which allows further reporting on the
 * distribution of project work and other stuff. Each activity belongs to a
 * category, a category represents a booking number but the tool allows to
 * define several activities that are distinguished in order to allow reports
 * based on booking numbers but also a finer grained inside into the work done
 * on a booking number
 */
public class Activity {
    private final String activityName;
    private final String bookingNumber;
    private final boolean projectActivity;

    /**
     * Create new line activity
     *
     * @param activityName
     *            Name of the new activity
     * @param bookingNumber
     *            booking number of the new activity
     * @return The freshly created activity. Non null since inability of
     *         creating the activity results in runtime exception
     */
    public static Activity newLineActivity(final String activityName, final String bookingNumber) {
	checkState(StringUtils.isNotBlank(activityName));
	checkState(StringUtils.isNotBlank(bookingNumber));
	return new Activity(activityName, bookingNumber, false);
    }

    /**
     * Create new project activity
     *
     * @param activityName
     *            Name of the new activity
     * @param bookingNumber
     *            booking number of the new activity
     * @return The freshly created activity. Non null since inability of
     *         creating the activity results in runtime exception
     */
    public static Activity newProjectActivity(final String activityName, final String bookingNumber) {
	checkState(StringUtils.isNotBlank(activityName));
	checkState(StringUtils.isNotBlank(bookingNumber));
	return new Activity(activityName, bookingNumber, true);
    }

    private Activity(final String activityName, final String bookingNumber, final boolean projectActivity) {
	this.activityName = activityName;
	this.bookingNumber = bookingNumber;
	this.projectActivity = projectActivity;
    }

    /**
     * @return Name of the activity. Non null
     */
    public String getActivityName() {
	return activityName;
    }

    /**
     * @return Booking number of the activities category. Non null
     */
    public String getBookingNumber() {
	return bookingNumber;
    }

    /**
     * @return Is the category a project activity?
     */
    public boolean isProjectActivity() {
	return projectActivity;
    }

    @Override
    public String toString() {
	return MoreObjects.toStringHelper(this).add("Booking Number", bookingNumber).add("Activity", activityName)
		.add("Is Project Activity", isProjectActivity()).toString();
    }

    @Override
    public boolean equals(final Object obj) {
	if (obj instanceof Activity) {
	    Activity act = (Activity) obj;
	    return Objects.equals(activityName, act.getActivityName())
		    && Objects.equals(bookingNumber, act.getBookingNumber())
		    && projectActivity == act.isProjectActivity();
	}
	return false;
    }

    @Override
    public int hashCode() {
	return Objects.hash(activityName, bookingNumber);
    }
}
