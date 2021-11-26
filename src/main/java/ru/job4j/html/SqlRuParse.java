package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.utils.Post;
import ru.job4j.utils.SqlRuDayTimeParser;
import ru.job4j.utils.DayTimeParser;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SqlRuParse implements Parse {

    private final DayTimeParser dayTimeParser;

    public SqlRuParse(DayTimeParser dayTimeParser) {
        this.dayTimeParser = dayTimeParser;
    }

    @Override
    public  List<Post> list(String url) {
        List<Post> list = new ArrayList<>();
        try {
            for (int i = 1; i <= 5; i++) {
                String string = String.format("%s/%d", url, i);
                Document doc = Jsoup.connect(string).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Post post =   detail(td.child(0).attr("href"));
                if (post.getTitle().toLowerCase().contains("java")
                        && !post.getTitle().toLowerCase().contains("javascript")) {
                    list.add(detail(td.child(0).attr("href")));
                }
            }
            }
        } catch (IOException e) {
               e.printStackTrace();
        }
        return list;
    }

    @Override
        public  Post detail(String link)  {
        Post post = null;
        try {
            Document doc = Jsoup.connect(link).get();
            Elements elements = doc.select(".msgTable");
            Element e = elements.get(0);
            String header = e.child(0).child(0).text();
            String description = e.child(0).child(1).text();
            String[]  date = e.child(0).child(2).child(0).text().split("\\[");
            LocalDateTime  localDateTime = dayTimeParser.parse(date[0].trim());
            post = new Post(header, description, link, localDateTime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return post;
    }

    public static void main(String[] args) {
        SqlRuParse salParse = new SqlRuParse(new SqlRuDayTimeParser());
            salParse.list("https://www.sql.ru/forum/job-offers").forEach(System.out::println);
        }
}