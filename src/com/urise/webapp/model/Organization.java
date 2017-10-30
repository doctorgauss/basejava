package com.urise.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Link homePage;
    private final List<Position> positions = new ArrayList<>();

    public Organization(String name, String url, Position ... positions) {
        this.homePage = new Link(name, url);
        Collections.addAll(this.positions, positions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Organization)) return false;

        Organization that = (Organization) o;

        return homePage.equals(that.homePage)
                && positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        int result = homePage.hashCode();
        result = 31 * result + positions.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "homePage=" + homePage +
                ", positions=" + positions +
                '}';
    }


    public static class Position implements Serializable{
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String title;
        private final String description;

        public Position(LocalDate startDate, String title, String description){
            this(startDate, LocalDate.now(), title, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String title, String description){
            Objects.requireNonNull(startDate, "Не задана дата начала работы в организации");
            Objects.requireNonNull(endDate, "Не задана дата окончания работы в организации");
            Objects.requireNonNull(title, "Отсутствует описание позиции");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position)) return false;

            Position position = (Position) o;

            return startDate.equals(position.startDate)
                    && endDate.equals(position.endDate)
                    && title.equals(position.title)
                    && description.equals(position.description);
        }

        @Override
        public int hashCode() {
            int result = startDate.hashCode();
            result = 31 * result + endDate.hashCode();
            result = 31 * result + title.hashCode();
            result = 31 * result + description.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
