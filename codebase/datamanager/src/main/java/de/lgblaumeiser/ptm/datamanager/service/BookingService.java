/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import java.time.LocalDate;
import java.time.LocalTime;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;

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
	 * @param activity
	 *            The activity of the booking
	 * @param starttime
	 *            The starttime of the booking
	 * @return The created Booking object, never null
	 * @throws IllegalStateException
	 *             If the starttime is already within a booked time or the
	 *             previous booking has already an endtime
	 */
	Booking addBooking(LocalDate bookingday, Activity activity, LocalTime starttime);

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
