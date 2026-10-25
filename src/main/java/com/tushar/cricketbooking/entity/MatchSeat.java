package com.tushar.cricketbooking.entity;

import java.math.BigDecimal;

import com.tushar.cricketbooking.enums.SeatStatus;
import jakarta.persistence.*;

@Entity
@Table(
    name = "match_seats",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"match_id", "seat_number"})         //This means the database will not allow the same seat number twice for the same match.
    }
)
public class MatchSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Version
    private Long version;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeatStatus status;

    public MatchSeat() {
    }

    public MatchSeat(Match match, String seatNumber,
    		BigDecimal  price, SeatStatus status) {
        this.match = match;
        this.seatNumber = seatNumber;
        this.price = price;
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    
    public Long getVersion() {
        return version;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public BigDecimal  getPrice() {
        return price;
    }

    public void setPrice(BigDecimal  price) {
        this.price = price;
    }

    public SeatStatus getStatus() {
        return status;
    }

    public void setStatus(SeatStatus status) {
        this.status = status;
    }
}