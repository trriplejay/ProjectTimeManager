/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import static java.util.Optional.of;

import java.time.LocalTime;
import java.util.Optional;

import com.beust.jcommander.IStringConverter;

class LocalTimeConverter implements IStringConverter<Optional<LocalTime>> {
	@Override
	public Optional<LocalTime> convert(final String s) {
		return of(LocalTime.parse(s));
	}
}
