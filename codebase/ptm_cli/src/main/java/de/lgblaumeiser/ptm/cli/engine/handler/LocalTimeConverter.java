/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import com.beust.jcommander.IStringConverter;

import java.time.LocalTime;
import java.util.Optional;

import static java.util.Optional.of;

public class LocalTimeConverter implements IStringConverter<Optional<LocalTime>> {
    @Override
    public Optional<LocalTime> convert(String s) {
        return of(LocalTime.parse(s));
    }
}
