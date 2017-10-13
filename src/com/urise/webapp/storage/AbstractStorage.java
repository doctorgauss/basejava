package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;

public abstract class AbstractStorage implements Storage {

    protected abstract void doUpdate(Resume r, Object searchKey);

    protected abstract boolean isExistSearchKey();

    protected abstract Object getSearchKey(String uuid);

    protected abstract void doSave();

    public void update(Resume r) {
        if (!check(r)) return;
        Object searchKey = getSearchKey(r.getUuid());
        if (!isExistSearchKey()) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            doUpdate(r, searchKey);
        }
    }

    public void save(Resume r) {
        if (!check(r)) return;
        Object searchKey = getSearchKey(r.getUuid());
        if (isExistSearchKey()) {
            throw new ExistStorageException(r.getUuid());
        } else doSave();
    }

    protected abstract void insertElement(Resume r, int index);

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            fillDeletedElement(index);
            storage[size - 1] = null;
            size--;
        }
    }

    protected abstract void fillDeletedElement(int index);

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);

    protected boolean check(Resume r) {
        if (r == null || r.getUuid() == null) {
            throw new StorageException("Резюме не соответствует формату", null);
        }
        return true;
    }

}
