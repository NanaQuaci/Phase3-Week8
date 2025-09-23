package com.projects.util;

public class ContactInfo {
    private final String name;
    private final String email;
    private final String message;

    public ContactInfo(String name, String email, String message) {
        this.name = name;
        this.email = email;
        this.message = message;
    }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMessage() { return message; }
}
