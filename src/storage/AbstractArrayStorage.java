package storage;

import model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size(){
        return size;
    }

    public Resume get(String uuid){
        int index = getIndex(uuid);
        if (index == -1){
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
