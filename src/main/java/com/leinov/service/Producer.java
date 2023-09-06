package com.leinov.service;

import com.leinov.entity.Command;

import java.util.concurrent.BlockingQueue;

import static com.leinov.entity.Command.SupportedCommand.*;

public class Producer {
    private final BlockingQueue<Command> queue;

    public Producer(BlockingQueue<Command> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            queue.put(new Command(ADD, 1, "a1", "Robert"));
            queue.put(new Command(ADD, 2, "a2", "Martin"));
            queue.put(new Command(PRINT_ALL, null, null, null));
            queue.put(new Command(DELETE_ALL, null, null, null));
            queue.put(new Command(PRINT_ALL, null, null, null));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
