/*
 * Copyright by Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 *
 * Licensed under MIT license
 */
package de.lgblaumeiser.ptm.analysis;

import java.util.Collection;

/**
 * Interface to be implemented for an anaylsis of the data
 */
public interface Analysis {
	Collection<Collection<Object>> analyze(Collection<String> parameter);
}
