package ru.igar15.votingsystem.util.exception;

public class VoteUpdateForbiddenException extends RuntimeException {
    public VoteUpdateForbiddenException(String message) {
        super(message);
    }
}
