package com.leinov.service;

import com.leinov.entity.Command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private BlockingQueue<Command> queue;
    private Statement statement;

    public Consumer(BlockingQueue<Command> queue, Statement statement) {
        this.queue = queue;
        this.statement = statement;
    }

    @Override
    public void run() {
        Command command;
        try {
            while (true) {
                command = queue.take();
                System.out.println("Consumer processed command " + command.getCommand());
                processCommand(command);
                Thread.sleep(1000); // only for demo
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private void processCommand(Command command) {
        try {
            switch (command.getCommand()) {
                case ADD:
                    statement.executeUpdate(command.generateSqlQuery());
                    break;
                case PRINT_ALL:
                    ResultSet tableData = statement.executeQuery(command.generateSqlQuery());
                    printRowsToTerminal(tableData);
                    break;
                case DELETE_ALL:
                    statement.executeUpdate(command.generateSqlQuery());
                    break;
                default:
                    break;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void printRowsToTerminal(ResultSet tableData) throws SQLException {
        // table head
        System.out.printf("| %-10s | %-10s | %-10s |%n", "USER_ID", "USER_GUID", "USER_NAME");
        while (tableData.next()) {
            System.out.printf("| %-10s | %-10s | %-10s |%n",
                    tableData.getString("USER_ID"),
                    tableData.getString("USER_GUID"),
                    tableData.getString("USER_NAME"));
        }
    }
}
