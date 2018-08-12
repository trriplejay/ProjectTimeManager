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
	 * @param comment
	 *            A comment for the booking, e.g., work done during the booking
	 * @return The created Booking object, never null
	 * @throws IllegalStateException
	 *             If the starttime is already within a booked time or the
	 *             previous booking has already an endtime
	 */
	Booking addBooking(LocalDate bookingday, String user, Activity activity, LocalTime starttime, String comment);

	/**
	 * Ends the given booking with the given endtime.
	 *
	 * @param booking
	 *            The booking for which an endtime is added
	 * @param endtime
	 *            The endtime of the booking
	 * @return The created Booking object, never null
	 * @throws IllegalStateException
	 *             If a booking overlap is added
	 */
	Booking endBooking(Booking booking, LocalTime endtime);
}
