package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;
    Resume r1 = new Resume("uuid1");
    Resume r2 = new Resume("uuid2");
    Resume r3 = new Resume("uuid3");
    Resume r4 = new Resume("uuid4");

    protected AbstractArrayStorageTest(Storage storage){
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
        Resume newResume = new Resume(r1.getUuid());
        storage.update(newResume);
        assertTrue(newResume == storage.get(newResume.getUuid()));
    }

    @Test (expected = NotExistStorageException.class)
    public void updateNotExist(){
        storage.update(new Resume("Batman"));
    }

    @Test
    public void save() throws Exception {
        assertEquals(3, storage.size());
        storage.save(r4);
        assertEquals(4, storage.size());
        assertTrue(r4 == storage.get(r4.getUuid()));
    }

    @Test (expected = ExistStorageException.class)
    public void saveExist() throws Exception{
        storage.save(r1);
    }

    @Test (expected = StorageException.class)
    public void saveNull() throws Exception{
        storage.save(null);
    }

    @Test (expected = StorageException.class)
    public void saveOverflow(){
        try{
            for(int i = storage.size(); i < AbstractArrayStorage.STORAGE_LIMIT; i++){
                storage.save(new Resume());
            }
        } catch (StorageException e){
            Assert.fail();
        }
        storage.save(new Resume());
    }

    @Test
    public void delete() throws Exception {
        assertEquals(3, storage.size());
        storage.delete(r1.getUuid());
        assertEquals(2, storage.size());
        try{
            storage.get(r1.getUuid());
            Assert.fail();
        } catch (StorageException e) {
        }
    }

    @Test (expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception{
        storage.delete("Batman");
    }

    @Test
    public void getAll() throws Exception {
        Resume[] resumes = storage.getAll();
        assertEquals(3, resumes.length);
        assertTrue(resumes[0] == r1);
        assertTrue(resumes[1] == r2);
        assertTrue(resumes[2] == r3);
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() throws Exception {
        assertTrue(r1 == storage.get(r1.getUuid()));
    }

    @Test (expected = NotExistStorageException.class)
    public void getNotExist() throws Exception{
        storage.get("Batman");
    }

}