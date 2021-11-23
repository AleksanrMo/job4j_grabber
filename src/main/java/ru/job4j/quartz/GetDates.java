package ru.job4j.quartz;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetDates {

    public void dates(String url, String hmlClass) throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements row = doc.select(hmlClass);
        List<String> strings = new ArrayList<>();
        for (Element td : row) {
            Element parent = td.parent();
            String st = parent.child(5).text();
            if(!strings.contains(st)) {
                strings.add(st);
            }
        }
        for(String dates: strings) {
            System.out.println(dates);
        }
    }
}

