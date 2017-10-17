package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>(0);

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected void doUpdate(Resume r, Object index) {
        storage.set((Integer) index, r);
    }

    @Override
    protected boolean isExistSearchKey(Object index) {
        return (Integer) index >= 0;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        if (uuid == null) return -1;
        for (int i = 0; i < storage.size(); i++){
            if (uuid.equals((storage.get(i).getUuid()))){
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Resume r, Object index) {
        storage.add(r);
    }

    @Override
    protected void doDelete(Object index) {
        storage.remove(((Integer) index).intValue());
    }

    @Override
    protected Resume doGet(Object index) {
        return storage.get((Integer) index);
    }
}
