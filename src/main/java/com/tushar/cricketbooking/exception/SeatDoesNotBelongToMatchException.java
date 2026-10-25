package com.tushar.cricketbooking.exception;

public class SeatDoesNotBelongToMatchException extends RuntimeException {

    public SeatDoesNotBelongToMatchException(String message) {
        super(message);
    }
}