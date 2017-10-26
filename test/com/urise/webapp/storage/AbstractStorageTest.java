package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    private Resume r1 = new Resume("Имя 1");
    private Resume r2 = new Resume("Имя 2");
    private Resume r3 = new Resume("Имя 3");
    private Resume r4 = new Resume("Имя 4");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.save(r1);
        storage.save(r2);
        storage.save(r3);
    }

    @After
    public void tearDown() throws Exception {
        storage.clear();
    }

    @Test
    public void clear() throws Exception {
        assertEquals(3, storage.size());
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        Resume newResume = new Resume(r1.getUuid(), "Новое имя");
        storage.update(newResume);
        assertTrue(newResume == storage.get(newResume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("Batman"));
    }

    @Test
    public void save() throws Exception {
        assertEquals(3, storage.size());
        storage.save(r4);
        assertEquals(4, storage.size());
        assertEquals(r4, storage.get(r4.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(r1);
    }

    @Test(expected = StorageException.class)
    public void saveNull() throws Exception {
        storage.save(null);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        assertEquals(3, storage.size());
        storage.delete(r1.getUuid());
        assertEquals(2, storage.size());
        storage.get(r1.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("Batman");
    }


    @Test
    public void getAllSorted() throws Exception {
        List<Resume> resumes = storage.getAllSorted();
        assertEquals(3, resumes.size());
        assertEquals(resumes, Arrays.asList(r1, r2, r3));
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() throws Exception {
        assertEquals(r1, storage.get(r1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("Batman");
    }
}