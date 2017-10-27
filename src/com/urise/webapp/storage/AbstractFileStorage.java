package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File dir;

    public AbstractFileStorage(File dir) {
        if (dir == null) {
            throw new RuntimeException("Директория для хранения файлов не задана");
        }
        if (!dir.exists() || !dir.isDirectory() || !dir.canRead() || dir.canWrite()){
            throw new RuntimeException("Директория не существует или нет доступа для записи/чтения файлов");
        }
        this.dir = dir;
    }

    @Override
    public void clear() {
        File[] resumes = dir.listFiles();
        if (dir == null) return;
        for(File r : resumes){
            doDelete(r);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        File[] resumes = dir.listFiles();
        if(resumes == null)
            throw new RuntimeException("Директория недоступна");
        return resumes.length;
    }

    @Override
    protected void doUpdate(Resume r, File f) {
        doWrite(r, f);
    }

    protected abstract void doWrite(Resume r, File f);

    @Override
    protected boolean isExistSearchKey(Integer index) {
        return index >= 0;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        if (uuid == null) return -1;
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals((storage.get(i).getUuid()))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void doSave(Resume r, File f) {
        try{
            f.createNewFile();
        } catch (IOException e){
            throw new RuntimeException("Ошибка при создании файла " + f.getAbsolutePath());
        }
        doUpdate(r, f);
    }

    @Override
    protected void doDelete(File resume) {
        resume.delete();
    }

    @Override
    protected Resume doGet(File resume) {
        return doRead(resume);
    }

    protected abstract Resume doRead(File resume);
}
