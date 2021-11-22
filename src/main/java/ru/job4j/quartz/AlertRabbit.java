package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {


    public static void main(String[] args) throws ClassNotFoundException {
        Properties config = new Properties();
        try (InputStream in = AlertRabbit .class.getClassLoader().getResourceAsStream("app.properties")) {
            config.load(in);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
            Class.forName(config.getProperty("driver-class-name"));
        try (Connection cn = DriverManager.getConnection(
                config.getProperty("url"),
                config.getProperty("username"),
                config.getProperty("password"))) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("connection", cn);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");
            Connection connection  = (Connection) context.getJobDetail().getJobDataMap().get("connection");
            try (Statement statement = connection.createStatement()) {
                statement.execute(
                        "insert into items(created_date) values(current_timestamp)"
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}