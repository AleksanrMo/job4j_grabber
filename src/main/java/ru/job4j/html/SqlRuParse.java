package ru.job4j.html;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.utils.SqlRuDayTimeParser;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        SqlRuDayTimeParser parser = new SqlRuDayTimeParser();
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".postslisttopic");
        for (Element td : row) {
            Element el = td.parent();
            Element href = td.child(0);
            System.out.println(href.text());
            System.out.println(href.attr("href"));
            System.out.println(parser.parse(el.child(5).text().trim()));
        }
    }
}