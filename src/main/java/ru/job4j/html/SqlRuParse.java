package ru.job4j.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect(
                "https://www.sql.ru/forum/1323240/sberbank-vedushhiy-inzhener-po-soprovozhdeniu-ot-120-000-rub").get();
        Elements row = doc.select(".msgFooter");
        for (Element td : row) {
            Element parent = td.parent();
            String[] st = parent.child(0).text().split("\\[");
            System.out.println(st[0]);
        }
    }
}
