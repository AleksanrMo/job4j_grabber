package ru.job4j.html;


import ru.job4j.utils.Post;
import ru.job4j.utils.SqlRuDayTimeParser;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public  PsqlStore(Properties cfg) {
        try (InputStream in = PsqlStore.class.getClassLoader().getResourceAsStream("app.properties")) {
                cfg.load(in);
                Class.forName(cfg.getProperty("driver-class-name"));
                cnn = DriverManager.getConnection(
                        cfg.getProperty("url"),
                        cfg.getProperty("username"),
                        cfg.getProperty("password"));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }

    }

    @Override
    public void save(Post post) {
        try (PreparedStatement statement = cnn.prepareStatement(
                "insert into posts(name, description, link, created) values(?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            statement.setTimestamp(4, Timestamp.valueOf(post.getCreation().withNano(0)));
            statement.execute();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> list = new ArrayList<>();
        try (PreparedStatement statement = cnn.prepareStatement("select * from posts")) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    list.add(returnResultSet(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Post returnResultSet(ResultSet result) throws SQLException {
        return new Post(
                result.getInt("Id"),
                result.getString("name"),
                result.getString("description"),
                result.getString("link"),
                result.getTimestamp("created").toLocalDateTime()
        );
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement statement = cnn.prepareStatement(
                "select * from posts where id = ?")) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    post = returnResultSet(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }


    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    public static void main(String[] args) {
        SqlRuParse salParse = new SqlRuParse(new SqlRuDayTimeParser());
        PsqlStore store = new PsqlStore(new Properties());
        List<Post> listOfPosts = salParse.list("https://www.sql.ru/forum/job-offers");
        /**
         * Записываем в базу результаты с сайта.
         */
        for (Post item: listOfPosts) {
            store.save(item);
        }
        /**
         * Получаем все что в находится в базе и выводим в консоль.
         */
        store.getAll().forEach(System.out::println);
        System.out.println("------------------------------------------------------------");
        /**
         * Получаем объект по индексу и выводим в консоль.
         */
        Post post =  store.findById(2);
        System.out.println(post);

   }
}
