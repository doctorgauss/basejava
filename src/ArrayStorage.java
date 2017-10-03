/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int sizeStorage = 0;

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

        if (findNumberResume(r.uuid) != null){
            System.out.println("Такое резюме уже есть в базе данных");
            return;
        }

        if (storage.length == sizeStorage){
            System.out.println("База резюме переполнена. Резюме не сохранено.");
            return;
        }
        storage[sizeStorage++] = r;
    }

    void update(Resume r){
        Integer numberResume = findNumberResume(r.uuid);
        if (numberResume == null) {
            System.out.println("Резюме не найдено");
            return;
        }
        storage[numberResume] = r;
    }

    Resume get(String uuid) {
        Integer numberResume = findNumberResume(uuid);
        if (numberResume == null) return null;
        return storage[numberResume];
    }

    void delete(String uuid) {
        Integer numberResume = findNumberResume(uuid);
        if (numberResume == null){
            System.out.println("Резюме не найдено.");
            return;
        }

        if (numberResume == sizeStorage - 1){
            storage[numberResume] = null;
        } else {
            storage[numberResume] = storage[sizeStorage-1];
            storage[sizeStorage-1] = null;
        }
        sizeStorage--;
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

    private Integer findNumberResume(String uuid){
        if (storage == null | uuid == null | sizeStorage == 0)
            return null;
        for(int i = 0; i < sizeStorage; i++){
            if (storage[i].uuid.equals(uuid))
                return i;
        }
        return null;
    }
}
