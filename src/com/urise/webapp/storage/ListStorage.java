package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
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
    protected void doUpdate(Resume r, Integer index) {
        storage.set(index, r);
    }

    @Override
    protected boolean isExist(Integer index) {
        return index >= 0;
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
    protected void doSave(Resume r, Integer index) {
        storage.add(r);
    }

    @Override
    protected void doDelete(Integer index) {
        storage.remove(index.intValue());
    }

    @Override
    protected Resume doGet(Integer index) {
        return storage.get(index);
    }
}
