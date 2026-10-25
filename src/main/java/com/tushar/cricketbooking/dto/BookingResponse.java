package com.tushar.cricketbooking.dto;

import com.tushar.cricketbooking.enums.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BookingResponse {

    private Long bookingId;
    private Long matchId;
    private String customerName;
    private List<String> seatNumbers;
    private BigDecimal totalAmount;
    private BookingStatus status;
    private LocalDateTime bookingTime;

    public BookingResponse() {
    }

    public BookingResponse(Long bookingId,
                           Long matchId,
                           String customerName,
                           List<String> seatNumbers,
                           BigDecimal totalAmount,
                           BookingStatus status,
                           LocalDateTime bookingTime) {
        this.bookingId = bookingId;
        this.matchId = matchId;
        this.customerName = customerName;
        this.seatNumbers = seatNumbers;
        this.totalAmount = totalAmount;
        this.status = status;
        this.bookingTime = bookingTime;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public Long getMatchId() {
        return matchId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public BigDecimal  getTotalAmount() {
        return totalAmount;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }
}
