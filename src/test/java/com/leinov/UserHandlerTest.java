package com.leinov;

import com.leinov.entity.Command;
import com.leinov.repository.UserRepository;
import com.leinov.service.CommandService;
import com.leinov.service.Consumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.leinov.entity.Command.SupportedCommand.*;

class UserHandlerTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void main() {
        UserRepository repository = new UserRepository("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        CommandService service = new CommandService(repository);

        BlockingQueue<Command> queue = new LinkedBlockingQueue<>();

        try {
            queue.put(new Command(ADD, 1, "a1", "Robert"));
            queue.put(new Command(ADD, 2, "a2", "Martin"));
            queue.put(new Command(PRINT_ALL, null, null, null));
            queue.put(new Command(DELETE_ALL, null, null, null));
            queue.put(new Command(PRINT_ALL, null, null, null));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Consumer consumer = new Consumer(queue, service);

        try {
            Thread thread = new Thread(consumer);
            thread.start();
            Thread.sleep(10000);
            thread.interrupt();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}