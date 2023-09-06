package com.leinov;

import com.leinov.entity.Command;
import com.leinov.service.Consumer;
import com.leinov.service.Producer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class UserHandler {
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE SUSERS(" +
            "USER_ID INT NOT NULL," +
            "USER_GUID VARCHAR(20) NOT NULL," +
            "USER_NAME VARCHAR(50) NOT NULL)";

    public static void main(String[] args) {
        Statement statement = connectDbAndCreateTable();

        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue, statement);

        producer.run();

        try (ExecutorService service = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors())) {
            // Consumer runs in a separate thread
            service.execute(consumer);
            Thread.sleep(10000); // only for demo
            Thread.currentThread().interrupt();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static Statement connectDbAndCreateTable() {
        Statement statement;
        try {
            Connection con = DriverManager.getConnection(getConnectionUrl());
            statement = con.createStatement();
            statement.executeUpdate(CREATE_TABLE_QUERY);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return statement;
    }

    private static String getConnectionUrl() {
        Properties prop = new Properties();
        try {
            prop.load(UserHandler.class.getClassLoader().getResourceAsStream("application.properties"));
            String url = prop.getProperty("url");
            if (url == null || url.isEmpty())
                throw new IOException("Not able to get connection URL");
            else
                return url;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
