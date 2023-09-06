package com.leinov;

import com.leinov.entity.Command;
import com.leinov.repository.UserRepository;
import com.leinov.service.CommandService;
import com.leinov.service.Consumer;
import com.leinov.service.Producer;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UserHandler {
    public static void main(String[] args) {
        UserRepository repository = new UserRepository(getConnectionUrl());
        CommandService service = new CommandService(repository);

        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();

        Producer producer = new Producer(queue);
        Consumer consumer = new Consumer(queue, service);

        producer.run();

        try {
            // Consumer runs in a separate thread
            Thread thread = new Thread(consumer);
            thread.start();
            Thread.sleep(10000); // only for demo
            thread.interrupt();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
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
