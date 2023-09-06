package com.leinov.service;

import com.leinov.entity.Command;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private final BlockingQueue<Command> queue;
    private final CommandService commandService;

    public Consumer(BlockingQueue<Command> queue, CommandService commandService) {
        this.queue = queue;
        this.commandService = commandService;
    }

    @Override
    public void run() {
        Command command;
        try {
            while (true) {
                command = queue.take();
                System.out.println("Consumer processed command " + command.getCommand());
                commandService.processCommand(command);
                Thread.sleep(1000); // only for demo
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
