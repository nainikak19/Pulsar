package com.pulsar.exception;

public class ErrorResponse {
    private String message;
    private int status;
    //private long timestamp;
    
    // Constructor
    public ErrorResponse(String message, int status, long timestamp) {
        this.message = message;
        this.status = status;
        //this.timestamp = timestamp;
    }
    
    // Default constructor
    public ErrorResponse() {}
    
    // Static factory method
    public static ErrorResponse of(String message, int status) {
        return new ErrorResponse(message, status, System.currentTimeMillis());
    }
    
    // Getters and Setters
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }
    
	/*
	 * public long getTimestamp() { return timestamp; }
	 * 
	 * public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
	 */
}