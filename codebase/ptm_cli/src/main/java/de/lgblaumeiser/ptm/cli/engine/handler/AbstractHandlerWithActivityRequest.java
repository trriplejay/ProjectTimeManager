/*
 * Copyright by Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
 *
 * Licensed under MIT license
 * 
 * SPDX-License-Identifier: MIT
 */
package de.lgblaumeiser.ptm.cli.engine.handler;

import java.util.Optional;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;

abstract class AbstractHandlerWithActivityRequest extends AbstractCommandHandler {
	protected Optional<Activity> getActivityById(final Long id) {
		if (id >= 0) {
			return getServices().getActivityStore().retrieveById(id);
		} else {
			return Optional.empty();
		}
	}
}
