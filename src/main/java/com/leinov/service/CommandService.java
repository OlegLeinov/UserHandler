package com.leinov.service;

import com.leinov.entity.Command;
import com.leinov.repository.UserRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommandService {
    private final UserRepository repository;

    public CommandService(UserRepository repository) {
        this.repository = repository;
    }

    public void processCommand(Command command) {
        try {
            switch (command.getCommand()) {
                case ADD -> repository.executeUpdate(repository.generateSqlQuery(command));
                case PRINT_ALL -> {
                    ResultSet tableData = repository.executeQuery(repository.generateSqlQuery(command));
                    printRowsToTerminal(tableData);
                }
                case DELETE_ALL -> repository.executeUpdate(repository.generateSqlQuery(command));
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
