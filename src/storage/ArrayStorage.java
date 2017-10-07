package storage;

import model.Resume;
import java.util.Arrays;


/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume r) {
        if (!check(r)) return;
        if (getIndex(r.getUuid()) != -1){
            System.out.println("Резюме " + r.getUuid() + " уже есть в базе");
        } else if (size >= STORAGE_LIMIT){
            System.out.println("База резюме переполнена");
        } else {
            storage[size] = r;
            size++;
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1){
            System.out.println("Резюме " + uuid + " не найдено");
        } else {
            storage[index] = storage[size-1];
            storage[size-1] = null;
            size--;
        }
    }

    protected int getIndex(String uuid){
        if (uuid == null) return -1;
        for (int i = 0; i < size; i++){
            if (uuid.equals((storage[i].getUuid()))){
                return i;
            }
        }
        return -1;
    }

}