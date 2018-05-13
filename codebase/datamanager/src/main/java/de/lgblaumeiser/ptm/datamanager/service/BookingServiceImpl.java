/*
 * Copyright 2016, 2017 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.ptm.datamanager.service;

import static com.google.common.base.Preconditions.checkState;
import static de.lgblaumeiser.ptm.datamanager.model.Booking.newBooking;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;

/**
 * The implementation of the DayBookings service
 */
public class BookingServiceImpl implements BookingService {
	private ObjectStore<Booking> bookingStore;

	@Override
	public Booking addBooking(LocalDate bookingday, String user, Activity activity, LocalTime starttime, String comment) {
		getLastOpenBooking(bookingday).ifPresent(b -> {
			Booking changed = endBooking(b, starttime);
			bookingStore.store(changed);
		});
		Booking newBooking = newBooking().setBookingday(bookingday).setUser(user).setStarttime(starttime)
				.setActivity(activity).setComment(comment).build();
		bookingStore.store(newBooking);
		return newBooking;
	}

	private Optional<Booking> getLastOpenBooking(LocalDate bookingday) {
		return bookingStore.retrieveAll().stream().filter(b -> b.getBookingday().equals(bookingday) && !b.hasEndtime())
				.findFirst();
	}

	@Override
	public Booking endBooking(final Booking booking, final LocalTime endtime) {
		checkState(booking != null);
		Booking endedBooking = booking.changeBooking().setEndtime(endtime).build();
		bookingStore.store(endedBooking);
		return endedBooking;
	}

	/**
	 * @param bookingStore
	 *            Set the booking store
	 */
	public BookingServiceImpl setBookingStore(ObjectStore<Booking> bookingStore) {
		this.bookingStore = bookingStore;
		return this;
	}
}
