package com.leinov.repository;

import java.sql.*;

public class UserRepository {
    private final Connection connection;
    private final Statement statement;
    private final String TABLE_NAME = "SUSERS"; // there is only one table in the demo app
    private final String FIELD_NAMES = "(USER_ID, USER_GUID, USER_NAME)"; // there is only one table in the demo app
    private final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME +
            "(USER_ID INT NOT NULL, " +
            "USER_GUID VARCHAR(20), " +
            "USER_NAME VARCHAR(50))";

    public UserRepository(String connectionUrl) {
        try {
            connection = DriverManager.getConnection(connectionUrl);
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_TABLE_QUERY);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int add(Integer payloadUserId, String payloadUserGuid, String payloadUserName) {
        try {
            return statement.executeUpdate(String.format("INSERT INTO %s %s VALUES (%d, '%s', '%s')",
                    TABLE_NAME,
                    FIELD_NAMES,
                    payloadUserId,
                    payloadUserGuid,
                    payloadUserName));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public ResultSet getAll() {
        try {
            return statement.executeQuery(String.format("SELECT * FROM %s", TABLE_NAME));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public int deleteAll() {
        try {
            return statement.executeUpdate(String.format("DELETE FROM %s", TABLE_NAME));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
