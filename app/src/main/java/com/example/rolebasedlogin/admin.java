package com.example.rolebasedlogin;

public class admin {

    private String username,email,password;
    public String USER_TYPE = "as";
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public admin(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
