package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.LinkedHashSet;


public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = doc.select(".altCol");
        LinkedHashSet<String> list = new LinkedHashSet();
        for (Element td : row) {
            Element parent = td.parent();
            String st = parent.child(5).text();
            list.add(st);
        }
        for(String dates: list) {
            System.out.println(dates);
        }
    }
}
