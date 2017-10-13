package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public abstract void clear();

    public abstract void update(Resume r);

    public abstract void save(Resume r);

    public abstract Resume get(String uuid);

    public abstract void delete(String uuid);

    public abstract Resume[] getAll();

    public abstract int size();

    protected boolean check(Resume r){
        if (r == null || r.getUuid() == null){
            throw new StorageException("Резюме не соответствует формату", null);
        }
        return true;
    }
}
