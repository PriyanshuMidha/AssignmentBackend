package com.example.AsisgnmentMongo.Exception;

public class ActionAlreadyPerformedException extends RuntimeException {
    public ActionAlreadyPerformedException(String message) {
        super(message);
    }
}
