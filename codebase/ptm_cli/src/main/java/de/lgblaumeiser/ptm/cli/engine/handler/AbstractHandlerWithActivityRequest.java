package de.lgblaumeiser.ptm.cli.engine.handler;

import de.lgblaumeiser.ptm.cli.engine.AbstractCommandHandler;
import de.lgblaumeiser.ptm.datamanager.model.Activity;

import java.util.Optional;

abstract class AbstractHandlerWithActivityRequest extends AbstractCommandHandler {
    protected Optional<Activity> getActivityById(final Long id) {
        if (id >= 0) {
            return getServices().getActivityStore().retrieveById(id);
        }
        else {
            return Optional.empty();
        }
    }
}
