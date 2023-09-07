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
                case ADD -> {
                    if (command.getPayloadUserId() == null) {
                        throw new IllegalArgumentException("USED_ID cannot be null");
                    }
                    repository.add(command.getPayloadUserId(),
                            command.getPayloadUserGuid(),
                            command.getPayloadUserName());
                }
                case PRINT_ALL -> {
                    ResultSet tableData = repository.getAll();
                    printRowsToTerminal(tableData);
                }
                case DELETE_ALL -> repository.deleteAll();
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
