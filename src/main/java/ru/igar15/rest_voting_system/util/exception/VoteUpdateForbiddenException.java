package ru.igar15.rest_voting_system.util.exception;

public class VoteUpdateForbiddenException extends RuntimeException {
    public VoteUpdateForbiddenException(String message) {
        super(message);
    }
}
