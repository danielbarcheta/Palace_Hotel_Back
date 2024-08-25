package com.hotelbooking.hotel.exception;

public class InvalidBookingRequestException extends RuntimeException {
    public InvalidBookingRequestException(String m) {
        super(m);
    }
}
