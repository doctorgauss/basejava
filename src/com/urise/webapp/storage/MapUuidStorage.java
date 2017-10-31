package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>(storage.values());
        Collections.sort(resumes);
        return resumes;
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void doUpdate(Resume r, String searchKey) {
        storage.put((String) searchKey, r);
    }

    @Override
    protected boolean isExist(String searchKey) {
        return searchKey != null;
    }

    @Override
    protected String getSearchKey(String uuid) {
        if (uuid == null) return null;
        Resume r = storage.get(uuid);
        return r == null ? null : r.getUuid();
    }

    @Override
    protected void doSave(Resume r, String searchKey) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }
}
