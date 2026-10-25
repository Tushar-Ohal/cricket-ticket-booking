package com.tushar.cricketbooking.exception;


public class BookingAlreadyCancelledException extends RuntimeException {

    public BookingAlreadyCancelledException(String message) {
        super(message);
    }
}
