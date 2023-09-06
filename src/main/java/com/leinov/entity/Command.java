package com.leinov.entity;

public class Command {
    public enum SupportedCommand {
        ADD,
        PRINT_ALL,
        DELETE_ALL
    }

    private final SupportedCommand command;
    private final Integer payloadUserId;
    private final String payloadUserGuid;
    private final String payloadUserName;

    public Command(SupportedCommand command, Integer payloadUserId, String payloadUserGuid, String payloadUserName) {
        this.command = command;
        this.payloadUserId = payloadUserId;
        this.payloadUserGuid = payloadUserGuid;
        this.payloadUserName = payloadUserName;
    }

    public SupportedCommand getCommand() {
        return command;
    }

    public Integer getPayloadUserId() {
        return payloadUserId;
    }

    public String getPayloadUserGuid() {
        return payloadUserGuid;
    }

    public String getPayloadUserName() {
        return payloadUserName;
    }

    @Override
    public String toString() {
        return switch (command) {
            case ADD -> "Add (" + payloadUserId + ", " + payloadUserGuid + ", " + payloadUserName + ")";
            case PRINT_ALL -> "PrintAll";
            case DELETE_ALL -> "DeleteAll";
        };
    }
}
