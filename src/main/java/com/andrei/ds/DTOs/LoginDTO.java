package com.andrei.ds.DTOs;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class LoginDTO {

    private UUID userId = null;

    @NotNull
    private String name;

    @NotNull
    private String password;

    @NotNull
    private String userType;

    public LoginDTO() {
    }

    public LoginDTO(@NotNull String name, @NotNull String password, @NotNull String userType) {
        this.name = name;
        this.password = password;
        this.userType = userType;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() {
        return userType;
    }
}
