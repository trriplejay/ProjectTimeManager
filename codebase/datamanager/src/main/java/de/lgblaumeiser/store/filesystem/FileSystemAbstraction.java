/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.filesystem;

import java.io.File;
import java.io.IOException;

import org.eclipse.jdt.annotation.NonNull;

/**
 * Interface to hide the concrete filesystem access implementation. Improved
 * testability for the business functionality
 */
public interface FileSystemAbstraction {
    void storeToFile(@NonNull File target, @NonNull String content) throws IOException;

    @NonNull
    String retrieveFromFile(@NonNull File source) throws IOException;
}
