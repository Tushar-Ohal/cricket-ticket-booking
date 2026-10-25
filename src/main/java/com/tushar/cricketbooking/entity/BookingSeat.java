package com.tushar.cricketbooking.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "booking_seats",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"booking_id", "match_seat_id"})
    }
)
public class BookingSeat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "match_seat_id", nullable = false)
    private MatchSeat matchSeat;

    public BookingSeat() {
    }

    public BookingSeat(Booking booking, MatchSeat matchSeat) {
        this.booking = booking;
        this.matchSeat = matchSeat;
    }

    public Long getId() {
        return id;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public MatchSeat getMatchSeat() {
        return matchSeat;
    }

    public void setMatchSeat(MatchSeat matchSeat) {
        this.matchSeat = matchSeat;
    }
}
