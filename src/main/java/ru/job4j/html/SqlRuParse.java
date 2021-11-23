package ru.job4j.html;


import ru.job4j.quartz.GetDates;

import java.util.ArrayList;
import java.util.List;


public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        GetDates datesFromSql = new GetDates();
        datesFromSql.dates(" https://www.sql.ru/forum/job-offers", ".altCol");

    }
}
