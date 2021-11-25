package ru.job4j.html;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.utils.Post;
import ru.job4j.utils.SqlRuDayTimeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        for (int i = 1; i <= 5; i++) {
            String string = String.format("https://www.sql.ru/forum/job-offers/%d", i);
            returnPosts(string).forEach(System.out::println);
        }
    }

    public static String getMessage(String url) throws IOException {
        Document doc = Jsoup.connect(
                url).get();
        Elements elements = doc.select(".msgBody");
        return elements.get(1).text();
    }

    public static List<Post> returnPosts(String url) throws IOException {
        SqlRuDayTimeParser parser = new SqlRuDayTimeParser();
        List<Post> list = new ArrayList<>();
        int id = 0;
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            id++;
            Element el = td.parent();
            Element href = td.child(0);
            getMessage(href.attr("href"));
            Post post = new Post(id, href.text(), href.attr("href"),
                    getMessage(href.attr("href")), parser.parse(el.child(5).text().trim()));
            list.add(post);
        }
        return list;
    }

}