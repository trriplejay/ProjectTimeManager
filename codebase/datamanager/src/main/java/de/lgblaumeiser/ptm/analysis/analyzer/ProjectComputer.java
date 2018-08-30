/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.analysis.analyzer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import de.lgblaumeiser.ptm.datamanager.model.Activity;
import de.lgblaumeiser.ptm.datamanager.model.Booking;
import de.lgblaumeiser.ptm.store.ObjectStore;

/**
 * An analysis to compute the amount of hours per a activity. The computer
 * calculates the percentage of an activity on the overall hours and maps these
 * percentages to the amount of 8 hours per booking day. This way, this fulfills
 * the requirements of the author concerning his time keeping.
 */
public class ProjectComputer extends AbstractBaseComputer {
	private ObjectStore<Activity> activityStore;

	@Override
	public Collection<Collection<Object>> analyze(final Collection<String> parameter) {
		Collection<Collection<Object>> result = new ArrayList<>();
		result.add(Arrays.asList("Activity", "Booking number", "Hours", "%"));
		Duration totalMinutes = Duration.ZERO;
		Map<Long, Duration> activityToMinutesMap = new HashMap<>();
		for (Booking current : getBookingsForPeriod(getCalculationPeriod(parameter))) {
			if (current.hasEndtime()) {
				Long currentActivity = current.getActivity();
				Duration accumulatedMinutes = activityToMinutesMap.get(currentActivity);
				if (accumulatedMinutes == null) {
					accumulatedMinutes = Duration.ZERO;
				}
				Duration activityLength = current.calculateTimeSpan().getLengthInMinutes();
				totalMinutes = totalMinutes.plus(activityLength);
				accumulatedMinutes = accumulatedMinutes.plus(activityLength);
				activityToMinutesMap.put(currentActivity, accumulatedMinutes);
			}
		}
		for (Entry<Long, Duration> currentActivity : activityToMinutesMap.entrySet()) {
			Long activityId = currentActivity.getKey();
			Duration totalMinutesId = currentActivity.getValue();
			double percentage = (double) totalMinutesId.toMinutes() / (double) totalMinutes.toMinutes();
			String percentageString = String.format("%2.1f", percentage * 100.0) + "%";
			Activity activity = activityStore.retrieveById(activityId).orElseThrow(IllegalStateException::new);
			result.add(Arrays.asList(activity.getActivityName(), activity.getBookingNumber(),
					formatDuration(totalMinutesId), percentageString));
		}
		result.add(Arrays.asList("Total", "", formatDuration(totalMinutes), "100%"));
		return result;
	}

	private Collection<Booking> getBookingsForPeriod(final CalculationPeriod period) {
		return store.retrieveAll().stream().filter(b -> isInPeriod(period, b.getBookingday()))
				.collect(Collectors.toList());
	}

	private String formatDuration(final Duration duration) {
		long minutes = duration.toMinutes();
		char pre = minutes < 0 ? '-' : ' ';
		minutes = Math.abs(minutes);
		return String.format("%c%02d:%02d", pre, minutes / 60, minutes % 60);
	}

	public ProjectComputer(final ObjectStore<Booking> bStore, ObjectStore<Activity> aStore) {
		super(bStore);
		activityStore = aStore;
	}
}
