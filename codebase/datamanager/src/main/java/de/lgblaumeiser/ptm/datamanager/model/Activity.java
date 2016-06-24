/*
 * Copyright 2015, 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.model;

import java.util.Map;
import java.util.Objects;

import org.eclipse.jdt.annotation.NonNull;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * Data structure for representation of an activity. An activity represents a
 * booking number under which work is done. Each work booking is assigned to
 * an activity and there are reports on hours spent on activities. Some activities
 * are considered as project activities and others are not, like vacation times.
 * This property is stored by a flag which allows further reporting on the
 * distribution of project work and other stuff. Each activity belongs to a category,
 * a category represents a booking number but the tool allows to define several
 * activities that are distinguished in order to allow reports based on booking
 * numbers but also a finer grained inside into the work done on a booking number
 */
public class Activity {
	private final String activityId;
	private final String categoryId;
	private final String bookingNumber;
	private final boolean projectActivity;

	private static final class BookingNumberInfo {
		String categoryId;
		boolean projectActivity;
		boolean withActivities;
		
		private BookingNumberInfo(String categoryId, boolean projectActivity, boolean withActivities) {
			this.categoryId = categoryId;
			this.projectActivity = projectActivity;
			this.withActivities = withActivities;
		}
	}
	
	private static Map<String, BookingNumberInfo> bookingNumberToInfoMap = Maps.newHashMap();
	
	/**
	 * Create new line activity with a category that uses sub activities
	 * 
	 * @param activityId Name of the activity
	 * @param categoryId Name of the category
	 * @param bookingNumber Booking Number of the category
	 * @return A new activity that represents the given data. The data is checked and an illegal argument exception is thrown
	 * @throws IllegalArgumentException If the data is not valid
	 */
	public static Activity newLineActivity(@NonNull String activityId, @NonNull String categoryId, @NonNull String bookingNumber) {
		checkCategoryOfBooking(activityId, categoryId, bookingNumber, false);
		return new Activity(activityId, categoryId, bookingNumber, false);
	}

	/**
	 * Create new line activity with a category that does not use sub activities
	 * 
	 * @param categoryId Name of the category
	 * @param bookingNumber Booking Number of the category
	 * @return A new activity that represents the given data. The data is checked and an illegal argument exception is thrown
	 * @throws IllegalArgumentException If the data is not valid
	 */
	public static Activity newLineActivity(@NonNull String categoryId, @NonNull String bookingNumber) {
		checkCategoryOfBooking(null, categoryId, bookingNumber, false);
		return new Activity(categoryId, bookingNumber, false);
	}

	/**
	 * Create new project activity with a category that uses sub activities
	 * 
	 * @param activityId Name of the activity
	 * @param categoryId Name of the category
	 * @param bookingNumber Booking Number of the category
	 * @return A new activity that represents the given data. The data is checked and an illegal argument exception is thrown
	 * @throws IllegalArgumentException If the data is not valid
	 */
	public static Activity newProjectActivity(@NonNull String activityId, @NonNull String categoryId, @NonNull String bookingNumber) {
		checkCategoryOfBooking(activityId, categoryId, bookingNumber, true);
		return new Activity(activityId, categoryId, bookingNumber, true);
	}

	/**
	 * Create new project activity with a category that does not use sub activities
	 * 
	 * @param categoryId Name of the category
	 * @param bookingNumber Booking Number of the category
	 * @return A new activity that represents the given data. The data is checked and an illegal argument exception is thrown
	 * @throws IllegalArgumentException If the data is not valid
	 */
	public static Activity newProjectActivity(@NonNull String categoryId, @NonNull String bookingNumber) {
		checkCategoryOfBooking(null, categoryId, bookingNumber, true);
		return new Activity(categoryId, bookingNumber, true);
	}

	private static void checkCategoryOfBooking(String activityId, String categoryId, String bookingNumber, boolean projectActivity) {
		BookingNumberInfo foundCategory = bookingNumberToInfoMap.get(bookingNumber);
		if(foundCategory != null) {
			Preconditions.checkArgument(categoryId.equals(foundCategory.categoryId));
			Preconditions.checkArgument(projectActivity == foundCategory.projectActivity);
			Preconditions.checkArgument((activityId != null) ? foundCategory.withActivities : !foundCategory.withActivities);
		}
	}
	
	private Activity(final String activityId, final String categoryId, final String bookingNumber, final boolean projectActivity) {
		this.activityId = activityId;
		this.categoryId = categoryId;
		this.bookingNumber = bookingNumber;
		this.projectActivity = projectActivity;
		BookingNumberInfo bookingInfo = bookingNumberToInfoMap.get(bookingNumber);
		if (bookingInfo == null) {
			bookingNumberToInfoMap.put(bookingNumber, new BookingNumberInfo(categoryId, projectActivity, true));
		}
	}

	private Activity(final String categoryId, final String bookingNumber, final boolean projectActivity) {
		this.activityId = categoryId;
		this.categoryId = categoryId;
		this.bookingNumber = bookingNumber;
		this.projectActivity = projectActivity;
		BookingNumberInfo bookingInfo = bookingNumberToInfoMap.get(bookingNumber);
		if (bookingInfo == null) {
			bookingNumberToInfoMap.put(bookingNumber, new BookingNumberInfo(categoryId, projectActivity, false));
		}
	}

	/**
	 * @return Name (resp. id) of the activity.
	 */
	public String getActivityId() {
		return activityId;
	}

	/**
	 * @return Name (resp. id) of activity category
	 */
	public String getCategoryId() {
		return categoryId;
	}

	/**
	 * @return Booking number of the activities category
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
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("Category", categoryId)
				.add("Booking Number", bookingNumber)
				.add("Activity", activityId)
				.add("Is Project Activity", isProjectActivity())
				.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Activity) {
			Activity act = (Activity)obj;
			return Objects.equals(activityId, act.getActivityId()) &&
				   Objects.equals(categoryId, act.getCategoryId()) &&
				   Objects.equals(bookingNumber, act.getBookingNumber()) &&
				   (projectActivity == act.isProjectActivity());
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
