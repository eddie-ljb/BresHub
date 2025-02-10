package de.etiennebader.breshub_engine.dao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class RegisterRequest {

    private String username;
    private String email;
    private String password;
    private Set<String> role;

    public RegisterRequest(String username, String email, String password, Set<String> role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public RegisterRequest() {}

    public String getUsername() {
        return username;
    }
    public void setUsername(String benutzername) {
        this.username = benutzername;
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
    public void setPassword(String passwort) {
        this.password = passwort;
    }
    public Set<String> getRole() {
        return role;
    }
    public void setRole(Set<String> role) {
        this.role = role;
    }
}
