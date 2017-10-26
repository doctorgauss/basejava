package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.*;

public class Organization {
    private final Link homePage;
    private final List<Position> positions = new ArrayList<>();

    public Organization(String name, String url, Position ... positions) {
        this.homePage = new Link(name, url);
        Collections.addAll(this.positions, positions);
    }


    public static class Position{
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Position(LocalDate startDate, LocalDate endDate, String title, String description){
            Objects.requireNonNull(startDate, "Не задана дата начала работы в организации");
            Objects.requireNonNull(endDate, "Не задана дата окончания работы в организации");
            Objects.requireNonNull(title, "Отсутствует описание позиции");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }
    }
}
