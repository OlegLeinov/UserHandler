package com.leinov.entity;

import java.util.Objects;

public class UserEntity {
    private Long userId;
    private String userGuid;
    private String userName;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserGuid() {
        return userGuid;
    }

    public void setUserGuid(String userGuid) {
        this.userGuid = userGuid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(userId, that.userId) && Objects.equals(userGuid, that.userGuid) && Objects.equals(userName, that.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userGuid, userName);
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", userGuid='" + userGuid + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
