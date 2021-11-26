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
    public  List<Post> list(String url) throws IOException {
        List<Post> list = new ArrayList<>();
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            list.add(detail(td.child(0).attr("href")));
        }
        return list;
    }

    @Override
        public  Post detail(String link) throws IOException {
        Document doc = Jsoup.connect(link).get();
            Elements elements = doc.select(".msgTable");
            Element e = elements.get(0);
            String header = e.child(0).child(0).text();
            String description = e.child(0).child(1).text();
            String[]  date = e.child(0).child(2).child(0).text().split("\\[");
            int id = Integer.parseInt(e.child(0).child(2).child(0).child(0).text());
            LocalDateTime  localDateTime = dayTimeParser.parse(date[0].trim());
            return new Post(id, header, link, description, localDateTime);

        }

    public static void main(String[] args) throws Exception {
        SqlRuParse salParse = new SqlRuParse(new SqlRuDayTimeParser());
        for (int i = 1; i <= 5; i++) {
            String string = String.format("https://www.sql.ru/forum/job-offers/%d", i);
            salParse.list(string).forEach(System.out::println);
        }
    }
}