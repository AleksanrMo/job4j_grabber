package ru.job4j.html;

import ru.job4j.utils.Post;
import java.util.List;

public interface Store {

    void save(Post post);

    List<Post> getAll();

    Post findById(int id);
}
