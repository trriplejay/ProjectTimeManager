/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import java.time.LocalDate;

import com.beust.jcommander.IStringConverter;

class LocalDateConverter implements IStringConverter<LocalDate> {
	@Override
	public LocalDate convert(final String s) {
		try {
			long deltaDays = Long.parseLong(s);
			LocalDate returnDate = LocalDate.now();
			if (deltaDays < 0) {
				returnDate = returnDate.minusDays(Math.abs(deltaDays));
			}
			return returnDate;
		} catch (NumberFormatException e) {
			return LocalDate.parse(s);
		}
	}
}
