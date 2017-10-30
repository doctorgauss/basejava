package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path directory;

    protected abstract void doWrite(Resume r, OutputStream os) throws IOException;

    protected abstract Resume doRead(InputStream is);

    public AbstractPathStorage(String dir) {
        if (dir == null) {
            throw new RuntimeException("Директория для хранения файлов не задана");
        }
        directory = Paths.get(dir);
        if (!Files.exists(directory)
                || !Files.isDirectory(directory)
                || !Files.isReadable(directory)
                || !Files.isWritable(directory)){
            throw new RuntimeException("Директория не существует или нет доступа для записи/чтения файлов");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при удалении директории");
        }
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new RuntimeException("Ошибка чтения директории");
        }
        return list.length;
    }

    @Override
    protected Path getSearchKey(String uuid) {
        if (uuid == null) return null;
        return new Path(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume r, Path f) {
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(f)));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при записи в файл");
        }
    }

    @Override
    protected boolean isExistSearchKey(Path resume) {
        return resume.exists();
    }

    @Override
    protected void doSave(Resume r, Path f) {
        try{
            if (!f.createNewFile())
                throw new IOException();
        } catch (IOException e){
            throw new RuntimeException("Ошибка при создании файла " + f.getAbsolutePath());
        }
        doUpdate(r, f);
    }

    @Override
    protected Resume doGet(Path resume) {
        try {
            return doRead(new BufferedInputStream(new FileInputStream(resume)));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка чтения файла");
        }
    }

    @Override
    protected void doDelete(Path resume) {
        try{
            if (!resume.delete()){
                throw new IOException();
            }
        } catch (IOException e){
            throw new RuntimeException("Ошибка при удалении файла " + resume.getAbsolutePath());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        Path[] files = directory.listFiles();
        if (files == null){
            throw new RuntimeException("Директория недоступна");
        }
        List<Resume> resumes = new ArrayList<>();
        for (Path f : files){
            resumes.add(doGet(f));
        }
        return resumes;
    }
}
