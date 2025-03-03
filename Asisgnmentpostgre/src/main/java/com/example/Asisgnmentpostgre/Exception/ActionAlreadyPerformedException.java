package com.example.Asisgnmentpostgre.Exception;

public class ActionAlreadyPerformedException extends RuntimeException {
    public ActionAlreadyPerformedException(String message) {
        super(message);
    }
}