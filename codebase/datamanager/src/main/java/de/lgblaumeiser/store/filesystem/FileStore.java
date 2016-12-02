/*
 * Copyright 2016 Lars Geyer-Blaumeiser <lgblaumeiser@gmail.com>
 */
package de.lgblaumeiser.store.filesystem;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;

import de.lgblaumeiser.store.AbstractObjectStore;

/**
 * A file base store for random objects
 */
public class FileStore<T> extends AbstractObjectStore<T> {
	public static final String STORAGE_PATH_KEY = "store";
	public static final String FILE_ENDING_KEY = "ending";

	private final Gson gsonUtil = new Gson();
	private FileSystemAbstraction filesystemAccess;

	@Override
	public void store(final T object) {
		checkNotNull(object);
		Object index = getIndexObject(object);
		File targetFile = getFileInformation(index);
		String content = createFileContent(object);

		try {
			filesystemAccess.storeToFile(targetFile, content);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public T retrieveByIndexKey(final Object key) {
		checkNotNull(key);
		File sourceFile = getFileInformation(key);
		if (!filesystemAccess.dataAvailable(sourceFile)) {
			return null;
		}
		String content;
		try {
			content = filesystemAccess.retrieveFromFile(sourceFile);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		T foundObj = extractFileContent(content);
		return foundObj;
	}

	private T extractFileContent(final String content) {
		return gsonUtil.fromJson(content, getDataClass());
	}

	private String createFileContent(final T object) {
		return gsonUtil.toJson(object);
	}

	private File getFileInformation(final Object index) {
		File store = getStore();
		return new File(store, index.toString() + getEnding());
	}

	private String getEnding() {
		String ending = getPropertyWithKey(FILE_ENDING_KEY);
		checkState(isNotBlank(ending));
		return "." + ending;
	}

	private File getStore() {
		File store = new File(getPropertyWithKey(STORAGE_PATH_KEY));
		checkState(store.exists() && store.isDirectory());
		return store;
	}

	@Override
	protected boolean allPropertiesSet() {
		return isNotBlank(getPropertyWithKey(STORAGE_PATH_KEY)) && isNotBlank(getPropertyWithKey(FILE_ENDING_KEY));
	}

	public void setFilesystemAccess(final FileSystemAbstraction filesystemAccess) {
		this.filesystemAccess = filesystemAccess;
	}
}
