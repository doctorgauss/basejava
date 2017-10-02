/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int sizeStorage = 0;

    void clear() {
        storage = new Resume[10000];
        sizeStorage = 0;
    }

    void save(Resume r) {
        if (r == null) return;
        if (storage == null){
            storage = new Resume[10000];
            sizeStorage = 0;
        }

        if (storage.length == sizeStorage){
            Resume[] newStorage = new Resume[storage.length + 10000];
            for (int i = 0; i < storage.length; i++){
                newStorage[i] = storage[i];
            }
            storage = newStorage;
        }
        storage[sizeStorage++] = r;
    }

    Resume get(String uuid) {
        if (storage == null | uuid == null)
            return null;
        for (int i = 0; i < sizeStorage; i++) {
            if (storage[i].toString().equals(uuid)){
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        if (storage == null | uuid == null | sizeStorage == 0)
            return;
        for (int i = 0; i < sizeStorage; i++){
            if (storage[i].toString() == uuid){
                if (i == sizeStorage - 1){
                    storage[i] = null;
                } else {
                    storage[i] = storage[sizeStorage-1];
                    storage[sizeStorage-1] = null;
                }
                sizeStorage--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        if (sizeStorage == 0){
            return  new Resume[0];
        }
        Resume[] newStorage = new Resume[sizeStorage];
        for (int i = 0; i < sizeStorage; i++) {
            newStorage[i] = storage[i];
        }
        return newStorage;
    }

    int size() {
        return sizeStorage;
    }
}
