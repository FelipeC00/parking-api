package com.example.demoparkapi.resources.dto;
/**
 * Data Transfer Object representing a user creation request.
 *
 * This class is used to capture the necessary information from the client when a request is made to create a new user account.
 */

public class UserRequestDto {
    private String username;
    private String password;

    public UserRequestDto() {
    }

    public UserRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRequestDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
