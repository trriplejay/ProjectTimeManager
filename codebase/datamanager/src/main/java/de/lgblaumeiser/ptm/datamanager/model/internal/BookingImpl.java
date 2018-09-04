/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.datamanager.model.internal;

import static de.lgblaumeiser.ptm.util.Utils.emptyString;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.util.Objects.hash;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import de.lgblaumeiser.ptm.datamanager.model.Booking;

/**
 * This class represents a booking on a day. It is represented by a starting
 * point in time and an ending point in time. An activity defines the action
 * done during that time. The action is a booking number on which work is booked
 * onto.
 */
public class BookingImpl implements Booking {
	private LocalDate bookingday;
	private LocalTime starttime;
	private LocalTime endtime;
	private Long activity;

	private String user;
	private String comment;
	private Long id;

	public BookingImpl(final Long id, final LocalDate bookingday, final String user, final LocalTime starttime,
			final LocalTime endtime, final Long activity, final String comment) {
		this.id = id;
		this.bookingday = bookingday;
		this.user = user;
		this.starttime = starttime;
		this.endtime = endtime;
		this.activity = activity;
		this.comment = comment;
	}

	public BookingImpl() {
		// Only needed for deserialization
	}

	/**
	 * 
	 * @return Bookingday of the booking, never null
	 */
	public LocalDate getBookingday() {
		return bookingday;
	}

	/**
	 * @param bookingday the bookingday to set
	 */
	public void setBookingday(LocalDate bookingday) {
		this.bookingday = bookingday;
	}

	/**
	 * @return Start time of the booking, never null
	 */
	public LocalTime getStarttime() {
		return starttime;
	}

	/**
	 * @param starttime the starttime to set
	 */
	public void setStarttime(LocalTime starttime) {
		this.starttime = starttime;
	}

	/**
	 * @return Whether booking already has an end time
	 */
	public boolean hasEndtime() {
		return endtime != null;
	}

	/**
	 * @param endtime the endtime to set
	 */
	public void setEndtime(LocalTime endtime) {
		this.endtime = endtime;
	}

	/**
	 * @return End time of the booking or null if not set
	 */
	public LocalTime getEndtime() {
		return endtime;
	}

	/**
	 * @return Activity of the booking, never null
	 */
	public Long getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(Long activity) {
		this.activity = activity;
	}

	/**
	 * @return User for whom booking was made, never null
	 */
	public String getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return A comment if available, an empty string of not, never null
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return The internal id of the booking. Automatically created by storage
	 *         system
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return hash(id, bookingday, starttime, endtime, activity, user, comment);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof BookingImpl) {
			BookingImpl bkg = (BookingImpl) obj;
			return id == bkg.id && bookingday.equals(bkg.bookingday) && starttime.equals(bkg.starttime)
					&& activity.equals(bkg.activity) && user.equals(bkg.user) && comment.equals(bkg.comment)
					&& endtime != null ? endtime.equals(bkg.endtime) : bkg.endtime == null;
		}
		return false;
	}

	@Override
	public String toString() {
		return format("Booking: Bookingday: %s, User: %s, Starttime: %s, %sActivity Id: %d, Comment: %s, Id: %d",
				bookingday.format(ISO_LOCAL_DATE), user, starttime.format(DateTimeFormatter.ofPattern("HH:mm")),
				endtime != null ? "Endtime: " + endtime.format(DateTimeFormatter.ofPattern("HH:mm")) + ", "
						: emptyString(),
				activity, comment, id);
	}
}
