package com.urise.webapp.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;

public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private final String uuid;
    private final String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContact(ContactType contactType){
        return contacts.get(contactType);
    }

    public Section getSection(SectionType sectionType){
        return sections.get(sectionType);
    }

    public void addContact(ContactType contactType, String contactValue){
        contacts.put(contactType, contactValue);
    }

    public void addSection(SectionType sectionType, Section section){
        sections.put(sectionType, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume r = (Resume) o;
        return uuid.equals(r.uuid) && fullName.equals(r.fullName);
    }

    @Override
    public int compareTo(Resume o) {
        int compareByFullName = fullName.compareTo(o.fullName);
        if (compareByFullName == 0)
            return uuid.compareTo(o.getUuid());
        return compareByFullName;
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }


}