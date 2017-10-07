package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void clear() {
        Arrays.fill(storage,0,size,null);
        size = 0;
    }

    @Override
    public void update(Resume r) {
        if (!check(r)) return;
        int index = getIndex(r.getUuid());
        if (index == -1){
            System.out.println("Резюме " + r.getUuid() + " не найдено");
        } else {
            storage[index] = r;
        }
    }

    @Override
    public void save(Resume r) {
        if (!check(r)) return;
        if (size >= storage.length){
            System.out.println("База переполнена");
            return;
        }
        int testIndex = getIndex(r.getUuid());
        if (getIndex(r.getUuid()) >= 0){
            System.out.println("Такое резюме уже есть в базе");
            return;
        }

        int index = 0;
        for (int i = 0; i < size; i++){
            if (r.compareTo(storage[i]) < 0){
                index = i;
                break;
            } else {
                index++;
            }
        }
        for (int i = size-1; i >= index; i--){
            storage[i+1] = storage[i];
        }
        storage[index] = r;
        size++;
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1){
            System.out.println("Резюме " + uuid + " не найдено");
            return;
        }
        storage[index] = null;
        if (index < size-1){
            for (int i = index; i < size; i++){
                storage[i] = storage[i+1];
            }
            storage[size-1] = null;
        }
        size--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
