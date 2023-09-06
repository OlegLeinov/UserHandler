package com.leinov.entity;

public class Command {
    public enum SupportedCommand {
        ADD,
        PRINT_ALL,
        DELETE_ALL
    }

    private SupportedCommand command;
    private Integer payloadUserId;
    private String payloadUserGuid;
    private String payloadUserName;
    private final String TABLE_NAME = "SUSERS"; // there is only one table in the demo app
    private final String FIELD_NAMES = "(USER_ID, USER_GUID, USER_NAME)"; // there is only one table in the demo app

    public Command(SupportedCommand command, Integer payloadUserId, String payloadUserGuid, String payloadUserName) {
        this.command = command;
        this.payloadUserId = payloadUserId;
        this.payloadUserGuid = payloadUserGuid;
        this.payloadUserName = payloadUserName;
    }

    public String generateSqlQuery() {
        return switch (command) {
            case ADD -> "INSERT INTO " + TABLE_NAME + FIELD_NAMES + " " +
                    "VALUES (" + payloadUserId + ", " +
                    shieldString(payloadUserGuid) + ", " +
                    shieldString(payloadUserName) + ")";
            case PRINT_ALL -> "SELECT * FROM " + TABLE_NAME;
            case DELETE_ALL -> "DELETE FROM " + TABLE_NAME;
        };
    }

    private String shieldString(String str) {
        return "'" + str + "'";
    }

    public SupportedCommand getCommand() {
        return command;
    }

    public void setCommand(SupportedCommand command) {
        this.command = command;
    }

    public Integer getPayloadUserId() {
        return payloadUserId;
    }

    public void setPayloadUserId(Integer payloadUserId) {
        this.payloadUserId = payloadUserId;
    }

    public String getPayloadUserGuid() {
        return payloadUserGuid;
    }

    public void setPayloadUserGuid(String payloadUserGuid) {
        this.payloadUserGuid = payloadUserGuid;
    }

    public String getPayloadUserName() {
        return payloadUserName;
    }

    public void setPayloadUserName(String payloadUserName) {
        this.payloadUserName = payloadUserName;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public String getFIELD_NAMES() {
        return FIELD_NAMES;
    }
}
