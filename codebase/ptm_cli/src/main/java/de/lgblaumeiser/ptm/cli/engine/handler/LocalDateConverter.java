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
		return LocalDate.parse(s);
	}
}
