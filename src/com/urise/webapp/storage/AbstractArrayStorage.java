package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage,0,size,null);
        size = 0;
    }

    public void update(Resume r){
        if (!check(r)) return;
        int index = getIndex(r.getUuid());
        if (index < 0){
            throw new NotExistStorageException(r.getUuid());
        } else {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        if (!check(r)) return;
        int index = getIndex(r.getUuid());
        if (index >= 0){
            throw new ExistStorageException(r.getUuid());
        } else if (size >= STORAGE_LIMIT){
            throw new StorageException("База резюме переполнена", r.getUuid());
        } else {
            insertElement(r, index);
            size++;
        }
    }

    protected abstract void insertElement(Resume r, int index);

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0){
            throw new NotExistStorageException(uuid);
        } else {
            fillDeletedElement(index);
            storage[size-1] = null;
            size--;
        }
    }

    protected abstract void fillDeletedElement(int index);

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size(){
        return size;
    }

    public Resume get(String uuid){
        int index = getIndex(uuid);
        if (index < 0){
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);

    protected boolean check(Resume r){
        if (r == null || r.getUuid() == null){
            throw new StorageException("Резюме не соответствует формату", null);
        }
        return true;
    }
}
