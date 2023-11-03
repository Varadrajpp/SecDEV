package com.javatechie.entity;
public class LogEntry {
    private String message;
    private String timestamp;

    // Constructors, getters, and setters

    public LogEntry() {
        // Default constructor
    }

    public LogEntry(String message, String timestamp) {
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
