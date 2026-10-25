package com.tushar.cricketbooking.dto;

import java.math.BigDecimal;

import com.tushar.cricketbooking.enums.SeatStatus;

public class SeatResponse {

    private Long id;
    private Long matchId;
    private String seatNumber;
    private BigDecimal price;
    private SeatStatus status;

    public SeatResponse() {
    }

    public SeatResponse(Long id, Long matchId, String seatNumber,
    		BigDecimal price, SeatStatus status) {
        this.id = id;
        this.matchId = matchId;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getMatchId() {
        return matchId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public SeatStatus getStatus() {
        return status;
    }
}
