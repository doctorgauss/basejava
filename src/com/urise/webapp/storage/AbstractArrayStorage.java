package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
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
            System.out.println("Резюме " + r.getUuid() + " не найдено");
        } else {
            storage[index] = r;
        }
    }

    public void save(Resume r) {
        if (!check(r)) return;
        int index = getIndex(r.getUuid());
        if (index >= 0){
            System.out.println("Резюме " + r.getUuid() + " уже есть в базе");
        } else if (size >= STORAGE_LIMIT){
            System.out.println("База резюме переполнена");
        } else {
            insertElement(r, index);
            size++;
        }
    }

    protected abstract void insertElement(Resume r, int index);

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0){
            System.out.println("Резюме " + uuid + " не найдено");
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
            System.out.println("Резюме " + uuid + " не найдено");
            return null;
        }
        return storage[index];
    }

    protected abstract int getIndex(String uuid);

    protected boolean check(Resume r){
        if (r == null || r.getUuid() == null){
            System.out.println("Резюме не соответствует формату");
            return false;
        }
        return true;
    }
}
