package com.urise.webapp.storage;

public class ObjectStreamStoragePathTest extends AbstractStorageTest {
    public ObjectStreamStoragePathTest(){
        super(new ObjectStreamStoragePath(STORAGE_PATH));
    }
}