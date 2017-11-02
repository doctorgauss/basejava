package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final String PATH_TO_STORAGE = "D:\\workspace_java\\basejava\\storage";
    protected final Storage storage;

    private static Resume R1;
    private static Resume R2;
    private static Resume R3;
    private static Resume R4;

    static {
        R1 = new Resume("Имя 1");
        R2 = new Resume("Имя 2");
        R3 = new Resume("Имя 3");
        R4 = new Resume("Имя 4");

        R1.addContact(ContactType.MAIL, "mail1@ya.ru");
        R1.addContact(ContactType.PHONE, "11111");
        R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Achivment11", "Achivment12", "Achivment13")));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Java", "SQL", "JavaScript")));
        R1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position(
                                        LocalDate.of(2005, Month.JANUARY, 1),
                                        "position1",
                                        "content1"),
                                new Organization.Position(
                                        LocalDate.of(2001, Month.MARCH, 1),
                                        LocalDate.of(2005, Month.JANUARY, 1),
                                        "position2",
                                        "content2")
                        )
                )
        );
        R1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(
                                        LocalDate.of(1996, Month.JANUARY, 1),
                                        LocalDate.of(2000, Month.DECEMBER, 1),
                                        "aspirant",
                                        null),
                                new Organization.Position(LocalDate.of(2001, Month.MARCH, 1),
                                        LocalDate.of(2005, Month.JANUARY, 1),
                                        "student",
                                        "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
        R2.addContact(ContactType.SKYPE, "skype2");
        R2.addContact(ContactType.PHONE, "22222");
        R1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://Organization2.ru",
                                new Organization.Position(LocalDate.of(2015, Month.JANUARY, 1), "position1", "content1"))));
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
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
        Resume newResume = new Resume(R1.getUuid(), "Новое имя");
        storage.update(newResume);
        assertTrue(newResume.equals(storage.get(newResume.getUuid())));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("Batman"));
    }

    @Test
    public void save() throws Exception {
        assertEquals(3, storage.size());
        storage.save(R4);
        assertEquals(4, storage.size());
        assertEquals(R4, storage.get(R4.getUuid()));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(R1);
    }

    @Test(expected = StorageException.class)
    public void saveNull() throws Exception {
        storage.save(null);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        assertEquals(3, storage.size());
        storage.delete(R1.getUuid());
        assertEquals(2, storage.size());
        storage.get(R1.getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("Batman");
    }


    @Test
    public void getAllSorted() throws Exception {
        List<Resume> resumes = storage.getAllSorted();
        assertEquals(3, resumes.size());
        assertEquals(resumes, Arrays.asList(R1, R2, R3));
    }

    @Test
    public void size() throws Exception {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() throws Exception {
        assertEquals(R1, storage.get(R1.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("Batman");
    }
}