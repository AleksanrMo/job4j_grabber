package ru.job4j.utils;

import java.time.LocalDateTime;
import java.util.Objects;

public class Post {

    private int id;
    private String title;
    private String link;
    private String description;
    private LocalDateTime creation;

    public Post(String title, String description, String link, LocalDateTime creation) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.creation = creation;
    }
    public Post(int id, String title, String description, String link, LocalDateTime creation) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.creation = creation;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreation() {
        return creation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return id == post.id && Objects.equals(title, post.title)
                && Objects.equals(link, post.link);
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, link);
    }

    @Override
    public String toString() {
        return "Post{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", link='" + description + '\''
                + ", description='" + link + '\''
                + ", creation=" + creation
                + '}';
    }

}
