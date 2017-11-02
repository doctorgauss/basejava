package com.urise.webapp.storage;

import java.nio.file.Paths;

public class PathStorageTest extends AbstractStorageTest {
    public PathStorageTest(){
        super(new PathStorage(Paths.get(PATH_TO_STORAGE), new ObjectStreamStorage()));
    }
}