package ru.igar15.votingsystem.util.exception;

public class IllegalRequestDataException extends RuntimeException{
    public IllegalRequestDataException(String message) {
        super(message);
    }
}