/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.datamanager.service;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Class which offers the services needed for entering the bookings of a day
 */
public interface BookingService {
	/**
	 * Add a booking at the corresponding starttime. If the last bogoking has no
	 * ended this booking ends the previous booking at the given starttime.
	 *
	 * @param bookingday
	 *            The day for which the booking should be created
	 * @param user
	 * 			  The user of the booking
	 * @param activity
	 *            The activity of the booking
	 * @param starttime
	 *            The starttime of the booking
	 * @param endtime
	 *            The endtime of the booking (optional)
	 * @param comment
	 *            A comment for the booking, e.g., work done during the booking (optional)
	 * @return The created Booking object, never null
	 * @throws IllegalStateException
	 *             If given data is not valid, e.g. endtime before starttime
	 */
	Booking addBooking(LocalDate bookingday, String user, Activity activity, LocalTime starttime,
					   Optional<LocalTime> endtime, Optional<String> comment);

	/**
	 * Changes the given booking, only user is not changeable. Only changed items are given as parameter,
	 * rest remains unchanged
	 *
	 * @param booking
	 *            The booking for which an endtime is added
	 * @param bookingday
	 *            The day for which the booking should be created (optional)
	 * @param activity
	 *            The activity of the booking (optional)
	 * @param starttime
	 *            The starttime of the booking(optional)
	 * @param endtime
	 *            The endtime of the booking (optional)
	 * @param comment
	 *            A comment for the booking, e.g., work done during the booking (optional)
	 * @return The changed Booking object, never null
	 * @throws IllegalStateException
	 *             If given data is not valid, e.g. endtime before starttime
	 */
	Booking changeBooking(Booking booking, Optional<LocalDate> bookingday, Optional<Activity> activity,
						  Optional<LocalTime> starttime, Optional<LocalTime> endtime, Optional<String> comment);
}
