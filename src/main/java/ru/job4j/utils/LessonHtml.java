package ru.job4j.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LessonHtml {
    public static void main(String[] args) throws IOException {

        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row2 = doc.select(".altCol");

        for (Element td : row2) {
            Element el = td.parent();
            Element href = el.child(1);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(el.child(5).text().trim());
        }
    }

}
