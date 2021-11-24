package ru.job4j.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public class SqlRuDayTimeParser implements DayTimeParser {
    private static final Map<String, String> MONTHS = Map.ofEntries(
            Map.entry("янв", "01"), Map.entry("фев", "02"),
            Map.entry("мар", "03"), Map.entry("апр", "04"),
            Map.entry("май", "05"), Map.entry("июн", "06"),
            Map.entry("июл", "07"), Map.entry("авг", "08"),
            Map.entry("сен", "09"), Map.entry("окт", "10"),
            Map.entry("ноя", "11"), Map.entry("дек", "12")
    );

    @Override
    public LocalDateTime parse(String dateForParse) {
        LocalDateTime localdateTime;
        LocalDate dayTime = LocalDate.now();
        String[] localDate = dayTime.toString().split("-");
        String[] temp = dateForParse.split(" ");
        String[] time = temp[temp.length - 1].split(":");

        if (temp[0].equals("сегодня,")) {
            localdateTime = LocalDateTime.of(
                    Integer.parseInt(localDate[0]),
                    Integer.parseInt(localDate[1]),
                    Integer.parseInt(localDate[2]),
                    Integer.parseInt(time[0]),
                    Integer.parseInt(time[1]));
        } else if (temp[0].equals("вчера,")) {
            localdateTime = LocalDateTime.of(
                    Integer.parseInt(localDate[0]),
                    Integer.parseInt(localDate[1]),
                    Integer.parseInt(localDate[2]) - 1,
                    Integer.parseInt(time[0]),
                    Integer.parseInt(time[1]));
        } else {
            localdateTime = LocalDateTime.of(
                    Integer.parseInt(temp[2].substring(0, 2)),
                    Integer.parseInt(MONTHS.get(temp[1])),
                    Integer.parseInt(temp[0]),
                    Integer.parseInt(time[0]),
                    Integer.parseInt(time[1]));
        }
        return localdateTime;
    }
}