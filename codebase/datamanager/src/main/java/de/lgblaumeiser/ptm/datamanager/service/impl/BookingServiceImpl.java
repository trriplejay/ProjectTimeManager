/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.datamanager.service.impl;

import static de.lgblaumeiser.ptm.datamanager.model.Booking.newBooking;
import static de.lgblaumeiser.ptm.util.Utils.assertState;
import static de.lgblaumeiser.ptm.util.Utils.stringHasContent;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.datamanager.service.BookingService;
import de.lgblaumeiser.ptm.store.ObjectStore;

/**
 * The implementation of the DayBookings service
 */
public class BookingServiceImpl implements BookingService {
	private static final int DEFAULTBREAKTIME = 30;

	private final ObjectStore<Booking> bookingStore;

	@Override
	public Booking addBooking(final LocalDate bookingday, final String user, final Activity activity,
			final LocalTime starttime, final Optional<LocalTime> endtime, final Optional<String> comment) {
		getLastOpenBooking(bookingday).ifPresent(b -> {
			if (b.getStarttime().isBefore(starttime)) {
				Booking changed = b.changeBooking().setEndtime(starttime).build();
				bookingStore.store(changed);
			}
		});
		assertState(!activity.isHidden());
		return createBooking(bookingday, user, activity.getId(), starttime, endtime, comment);
	}

	private Booking createBooking(final LocalDate bookingday, final String user, final Long activity,
			final LocalTime starttime, final Optional<LocalTime> endtime, final Optional<String> comment) {
		Booking.BookingBuilder newBookingBuilder = newBooking().setBookingday(bookingday).setUser(user)
				.setStarttime(starttime).setActivity(activity);
		endtime.ifPresent(newBookingBuilder::setEndtime);
		comment.ifPresent(c -> {
			if (stringHasContent(c))
				newBookingBuilder.setComment(c);
		});
		Booking newBooking = newBookingBuilder.build();
		bookingStore.store(newBooking);
		return newBooking;
	}

	private Optional<Booking> getLastOpenBooking(final LocalDate bookingday) {
		return bookingStore.retrieveAll().stream().filter(b -> b.getBookingday().equals(bookingday) && !b.hasEndtime())
				.findFirst();
	}

	@Override
	public Booking changeBooking(final Booking booking, final Optional<LocalDate> bookingday,
			final Optional<Activity> activity, final Optional<LocalTime> starttime, final Optional<LocalTime> endtime,
			final Optional<String> comment) {
		assertState(booking != null);
		Booking.BookingBuilder bookingBuilder = booking.changeBooking();
		bookingday.ifPresent(bookingBuilder::setBookingday);
		activity.ifPresent(a -> {
			assertState(!a.isHidden());
			bookingBuilder.setActivity(a.getId());
		});
		starttime.ifPresent(bookingBuilder::setStarttime);
		endtime.ifPresent(bookingBuilder::setEndtime);
		comment.ifPresent(c -> {
			if (stringHasContent(c))
				bookingBuilder.setComment(c);
		});
		Booking changedBooking = bookingBuilder.build();
		bookingStore.store(changedBooking);
		return changedBooking;
	}

	@Override
	public Booking addBreakToBooking(Booking booking, LocalTime breakstart, Optional<Integer> duration) {
		assertState(booking != null);
		assertState(breakstart != null);
		assertState(duration != null);
		long internalDuration = duration.orElse(DEFAULTBREAKTIME);
		checkParameters(booking, breakstart, internalDuration);
		changeBooking(booking, Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(breakstart),
				Optional.empty());
		Booking afterBreak = createBooking(booking.getBookingday(), booking.getUser(), booking.getActivity(),
				breakstart.plusMinutes(internalDuration), Optional.ofNullable(booking.getEndtime()),
				Optional.of(booking.getComment()));
		return afterBreak;
	}

	private void checkParameters(Booking booking, LocalTime breakstart, long internalDuration) {
		assertState(booking.getStarttime().isBefore(breakstart));
		assertState(booking.getEndtime() != null);
		assertState(booking.getEndtime().isAfter(breakstart.plusMinutes(internalDuration)));
	}

	/**
	 * @param bookingStore Set the booking store
	 */
	public BookingServiceImpl(final ObjectStore<Booking> bookingStore) {
		this.bookingStore = bookingStore;
	}
}
