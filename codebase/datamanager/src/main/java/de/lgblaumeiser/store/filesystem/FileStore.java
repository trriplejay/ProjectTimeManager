/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.filesystem;

import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jdt.annotation.NonNull;

import com.google.gson.Gson;

import de.lgblaumeiser.store.AbstractObjectStore;

/**
 * A file base store for random objects
 */
public class FileStore<T> extends AbstractObjectStore<T> {
    public static final String STORAGE_PATH_KEY = "store";
    private static final String FILE_ENDING = ".ptmjs";

    private final Gson gsonUtil = new Gson();
    private FileSystemAbstraction filesystemAccess;

    @SuppressWarnings("null")
    @Override
    public void store(@NonNull final T object) {
	Object index = getIndexObject(object);
	File store = getStore();
	File targetFile = new File(store, index.toString() + FILE_ENDING);
	String json = gsonUtil.toJson(object);

	try {
	    filesystemAccess.storeToFile(targetFile, json);
	} catch (IOException e) {
	    throw new IllegalStateException(e);
	}
    }

    @Override
    public @NonNull Collection<T> retrieveByIndexKey(@NonNull final String key) {
	// TODO Auto-generated method stub
	return null;
    }

    private File getStore() {
	File store = new File(getPropertyWithKey(STORAGE_PATH_KEY));
	checkState(store.exists() && store.isDirectory());
	return store;
    }

    @Override
    protected boolean allPropertiesSet() {
	return isNotBlank(getPropertyWithKey(STORAGE_PATH_KEY));
    }

    void setFilesystemAccess(final FileSystemAbstraction filesystemAccess) {
	this.filesystemAccess = filesystemAccess;
    }
}
