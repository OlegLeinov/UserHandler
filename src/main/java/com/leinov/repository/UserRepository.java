package com.leinov.repository;

import com.leinov.entity.Command;

import java.sql.*;

public class UserRepository {
    private final Connection connection;
    private final Statement statement;
    private final String TABLE_NAME = "SUSERS"; // there is only one table in the demo app
    private final String FIELD_NAMES = "(USER_ID, USER_GUID, USER_NAME)"; // there is only one table in the demo app
    private final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME +
            "(USER_ID INT NOT NULL," +
            "USER_GUID VARCHAR(20) NOT NULL," +
            "USER_NAME VARCHAR(50) NOT NULL)";

    public UserRepository(String connectionUrl) {
        try {
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_TABLE_QUERY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet executeQuery(String query) {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int executeUpdate(String query) {
        try {
            return statement.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public String generateSqlQuery(Command command) {
        return switch (command.getCommand()) {
            case ADD -> "INSERT INTO " + TABLE_NAME + FIELD_NAMES + " " +
                    "VALUES (" + command.getPayloadUserId() + ", " +
                    shieldString(command.getPayloadUserGuid()) + ", " +
                    shieldString(command.getPayloadUserName()) + ")";
            case PRINT_ALL -> "SELECT * FROM " + TABLE_NAME;
            case DELETE_ALL -> "DELETE FROM " + TABLE_NAME;
        };
    }

    private String shieldString(String str) {
        return "'" + str + "'";
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public String getFIELD_NAMES() {
        return FIELD_NAMES;
    }

    public String getCREATE_TABLE_QUERY() {
        return CREATE_TABLE_QUERY;
    }
}
