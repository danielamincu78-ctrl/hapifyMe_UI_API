package com.hapifyme.api.models;

public class LoginRequest {

    private String username;
    private String password;

    // Constructor
    public LoginRequest(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    // Getters și Setters (obligatorii pentru Jackson)
    public String getUsername() { return username; }
    public void setUsername(String username) {
        if(!username.isEmpty()) {
            this.username = username;
        } else throw new IllegalArgumentException("Username cannot be empty");
    }
    public String getPassword() { return password; }
    public void setPassword(String password) {
        if(password.length() > 7 && password.contains("@")) {
            this.password = password;
        }
    }
}
