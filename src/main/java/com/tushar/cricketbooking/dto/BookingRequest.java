package com.tushar.cricketbooking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class BookingRequest {

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotEmpty(message = "At least one seat must be selected")
    private List<Long> seatIds;

    public BookingRequest() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<Long> getSeatIds() {
        return seatIds;
    }

    public void setSeatIds(List<Long> seatIds) {
        this.seatIds = seatIds;
    }
}
