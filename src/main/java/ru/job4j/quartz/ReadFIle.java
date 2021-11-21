package ru.job4j.quartz;

import java.io.BufferedReader;
import java.io.FileReader;

public class ReadFIle {

    public int time(String file) {
        int rsl = 0;
         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String[] st = reader.readLine().split("=");
            rsl = Integer.parseInt(st[1]);
         } catch (Exception e) {
             e.printStackTrace();
         }
         return rsl;
    }
}
