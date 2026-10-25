package com.tushar.cricketbooking.controller;

import com.tushar.cricketbooking.dto.BookingRequest;
import com.tushar.cricketbooking.dto.BookingResponse;
import com.tushar.cricketbooking.service.BookingService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/matches/{matchId}")
    public ResponseEntity<BookingResponse> createBooking(
            @PathVariable Long matchId,
            @Valid @RequestBody BookingRequest request) {

        BookingResponse response =
                bookingService.createBooking(matchId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingById(
            @PathVariable Long bookingId) {

        BookingResponse response =
                bookingService.getBookingById(bookingId);

        return ResponseEntity.ok(response);
    }
    
    
    
    @PostMapping("/{bookingId}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.noContent().build();
    }
}