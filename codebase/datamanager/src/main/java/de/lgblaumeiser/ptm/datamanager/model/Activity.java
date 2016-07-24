/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import static com.google.common.base.Preconditions.checkState;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.annotation.NonNull;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Maps;

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
    @NonNull
    private final String activityId;
    @NonNull
    private final String categoryId;
    @NonNull
    private final String bookingNumber;
    private final boolean projectActivity;

    private static final class BookingNumberInfo {
	String categoryId;
	boolean projectActivity;
	boolean withActivities;

	private BookingNumberInfo(final String categoryId, final boolean projectActivity,
		final boolean withActivities) {
	    this.categoryId = categoryId;
	    this.projectActivity = projectActivity;
	    this.withActivities = withActivities;
	}
    }

    private static Map<String, BookingNumberInfo> bookingNumberToInfoMap = Maps.newHashMap();

    public static class ActivityBuilder {
	private String categoryId;
	private String activityId;
	private String bookingNumber;
	private boolean projectActivity = false;

	@NonNull
	public ActivityBuilder setCategoryId(@NonNull final String categoryId) {
	    this.categoryId = categoryId;
	    return this;
	}

	@NonNull
	public ActivityBuilder setActivityId(@NonNull final String activityId) {
	    this.activityId = activityId;
	    return this;
	}

	@NonNull
	public ActivityBuilder setBookingNumber(final String bookingNumber) {
	    this.bookingNumber = bookingNumber;
	    return this;
	}

	@NonNull
	public ActivityBuilder setProjectActivity() {
	    this.projectActivity = true;
	    return this;
	}

	@NonNull
	public ActivityBuilder setLineActivity() {
	    this.projectActivity = false;
	    return this;
	}

	@SuppressWarnings("null")
	@NonNull
	public Activity build() {
	    checkCategoryOfBooking();
	    if (StringUtils.isBlank(activityId)) {
		activityId = categoryId;
	    }
	    return new Activity(activityId, categoryId, bookingNumber, projectActivity);
	}

	private void checkCategoryOfBooking() {
	    checkState(StringUtils.isNotBlank(categoryId));
	    checkState(StringUtils.isNotBlank(bookingNumber));
	    BookingNumberInfo foundCategory = bookingNumberToInfoMap.get(bookingNumber);
	    if (foundCategory != null) {
		checkState(categoryId.equals(foundCategory.categoryId));
		checkState(projectActivity == foundCategory.projectActivity);
		checkState(activityId != null ? foundCategory.withActivities : !foundCategory.withActivities);
	    }
	}
    }

    public static ActivityBuilder newActivity() {
	return new ActivityBuilder();
    }

    private Activity(@NonNull final String activityId, @NonNull final String categoryId,
	    @NonNull final String bookingNumber, final boolean projectActivity) {
	this.activityId = activityId;
	this.categoryId = categoryId;
	this.bookingNumber = bookingNumber;
	this.projectActivity = projectActivity;
	BookingNumberInfo bookingInfo = bookingNumberToInfoMap.get(bookingNumber);
	if (bookingInfo == null) {
	    bookingNumberToInfoMap.put(bookingNumber,
		    new BookingNumberInfo(categoryId, projectActivity, activityId != categoryId));
	}
    }

    /**
     * @return Name (resp. id) of the activity.
     */
    @NonNull
    public String getActivityId() {
	return activityId;
    }

    /**
     * @return Name (resp. id) of activity category
     */
    @NonNull
    public String getCategoryId() {
	return categoryId;
    }

    /**
     * @return Booking number of the activities category
     */
    @NonNull
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
	return MoreObjects.toStringHelper(this).omitNullValues().add("Category", categoryId)
		.add("Booking Number", bookingNumber).add("Activity", activityId)
		.add("Is Project Activity", isProjectActivity()).toString();
    }

    @Override
    public boolean equals(final Object obj) {
	if (obj instanceof Activity) {
	    Activity act = (Activity) obj;
	    return Objects.equals(activityId, act.getActivityId()) && Objects.equals(categoryId, act.getCategoryId())
		    && Objects.equals(bookingNumber, act.getBookingNumber())
		    && projectActivity == act.isProjectActivity();
	}
	return false;
    }

    @Override
    public int hashCode() {
	return Objects.hash(activityId, categoryId, bookingNumber);
    }

    /**
     * Current possibility of resetting the business rules. This is not a final
     * solution
     */
    static void reset() {
	bookingNumberToInfoMap.clear();
    }
}
