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
        if (resumes == null) return;
        for(File r : resumes){
            doDelete(r);
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] files = dir.listFiles();
        if (files == null){
            throw new RuntimeException("Директория недоступна");
        }
        List<Resume> resumes = new ArrayList<>();
        for (File f : files){
            resumes.add(doGet(f));
        }
        return resumes;
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
    protected boolean isExistSearchKey(File resume) {
        return resume.exists();
    }

    @Override
    protected File getSearchKey(String uuid) {
        if (uuid == null) return null;
        return new File(dir, uuid);
    }

    @Override
    protected void doSave(Resume r, File f) {
        try{
            if (!f.createNewFile())
                throw new IOException();
        } catch (IOException e){
            throw new RuntimeException("Ошибка при создании файла " + f.getAbsolutePath());
        }
        doUpdate(r, f);
    }

    @Override
    protected void doDelete(File resume) {
        try{
            if (!resume.delete()){
                throw new IOException();
            }
        } catch (IOException e){
            throw new RuntimeException("Ошибка при удалении файла " + resume.getAbsolutePath());
        }
    }

    @Override
    protected Resume doGet(File resume) {
        return doRead(resume);
    }

    protected abstract Resume doRead(File resume);
}
