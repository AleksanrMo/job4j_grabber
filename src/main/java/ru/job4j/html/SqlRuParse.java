package ru.job4j.html;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import ru.job4j.utils.SqlRuDayTimeParser;

import java.io.IOException;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        SqlRuDayTimeParser parser = new SqlRuDayTimeParser();
        for (int i = 1; i <= 5; i++) {
            String string = String.format("https://www.sql.ru/forum/job-offers/%d", i);
            Document doc = Jsoup.connect(string).get();
            Elements row = doc.select(".postslisttopic");
            for (Element td : row) {
                Element el = td.parent();
                Element href = td.child(0);
                System.out.println(href.text());
                getMessage(href.attr("href"));
                System.out.println(parser.parse(el.child(5).text().trim()));
                System.out.println("--------------------------------");
            }
        }
    }

    public  static  void getMessage(String url) throws IOException {
        Document doc = Jsoup.connect(
                "https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t").get();
        Elements elements = doc.select(".msgBody");
        for (Element el: elements) {
            System.out.println(el.text());
        }
    }
}