package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(Resume r, int index) {
        for (int i = size-1; i >= index; i--){
            storage[i+1] = storage[i];
        }
        storage[index] = r;
    }

    @Override
    protected void fillDeletedElement(int index) {
        storage[index] = null;
        if (index < size-1){
            for (int i = index; i < size; i++){
                storage[i] = storage[i+1];
            }
            storage[size-1] = null;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
