/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.filesystem;

import java.io.File;
import java.io.IOException;

/**
 * Interface to hide the concrete filesystem access implementation. Improved
 * testability for the business functionality
 */
public interface FileSystemAbstraction {
    /**
     * @param target
     *            File reference in which to store content
     * @param content
     *            Content to store
     * @throws IOException
     *             If access fails
     */
    void storeToFile(File target, String content) throws IOException;

    /**
     * @param source
     *            File to read
     * @return Content of file as string, might be empty
     * @throws IOException
     *             If access fails
     */
    String retrieveFromFile(File source) throws IOException;

    /**
     * @param source
     *            File to check whether it exists
     * @return True, if file really exists
     */
    boolean dataAvailable(File source);
}
