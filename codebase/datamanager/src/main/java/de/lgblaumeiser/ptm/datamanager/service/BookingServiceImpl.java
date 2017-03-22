/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static de.lgblaumeiser.ptm.datamanager.model.Booking.newBooking;

import java.time.LocalTime;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.model.DayBookings;

/**
 * The implementation of the DayBookings service
 */
public class BookingServiceImpl implements BookingService {
	@Override
	public Booking addBooking(final DayBookings dayBookings, final Activity activity, final LocalTime starttime) {
		checkNotNull(dayBookings);
		checkNotNull(activity);
		checkNotNull(starttime);
		Booking lastBooking = dayBookings.getLastBooking();
		if (lastBooking != null && !lastBooking.hasEndtime()) {
			endBooking(dayBookings, lastBooking, starttime);
		}
		Booking newBooking = newBooking().setStarttime(starttime).setActivity(activity).build();
		dayBookings.addBooking(newBooking);
		return newBooking;
	}

	@Override
	public Booking endBooking(final DayBookings dayBookings, final Booking booking, final LocalTime endtime) {
		checkNotNull(dayBookings);
		checkNotNull(booking);
		checkNotNull(endtime);
		checkState(dayBookings.getBookings().contains(booking));
		checkState(!endtime.equals(booking.getEndtime()));
		Booking endedBooking = booking.changeBooking().setEndtime(endtime).build();
		dayBookings.replaceBooking(booking, endedBooking);
		return endedBooking;
	}

	@Override
	public void removeBooking(final DayBookings dayBookings, final Booking booking) {
		checkNotNull(dayBookings);
		checkNotNull(booking);
		checkState(dayBookings.getBookings().contains(booking));
		dayBookings.removeBooking(booking);
	}
}
