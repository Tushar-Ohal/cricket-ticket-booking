package com.tushar.cricketbooking.service;

import com.tushar.cricketbooking.dto.BookingRequest;
import com.tushar.cricketbooking.dto.BookingResponse;
import com.tushar.cricketbooking.entity.Booking;
import com.tushar.cricketbooking.entity.BookingSeat;
import com.tushar.cricketbooking.entity.Match;
import com.tushar.cricketbooking.entity.MatchSeat;
import com.tushar.cricketbooking.enums.BookingStatus;
import com.tushar.cricketbooking.enums.SeatStatus;
import com.tushar.cricketbooking.exception.BookingAlreadyCancelledException;
import com.tushar.cricketbooking.exception.DuplicateSeatException;
import com.tushar.cricketbooking.exception.ResourceNotFoundException;
import com.tushar.cricketbooking.exception.SeatAlreadyBookedException;
import com.tushar.cricketbooking.exception.SeatDoesNotBelongToMatchException;
import com.tushar.cricketbooking.repository.BookingRepository;
import com.tushar.cricketbooking.repository.BookingSeatRepository;
import com.tushar.cricketbooking.repository.MatchRepository;
import com.tushar.cricketbooking.repository.MatchSeatRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingSeatRepository bookingSeatRepository;
    private final MatchRepository matchRepository;
    private final MatchSeatRepository matchSeatRepository;

    public BookingService(BookingRepository bookingRepository,
                          BookingSeatRepository bookingSeatRepository,
                          MatchRepository matchRepository,
                          MatchSeatRepository matchSeatRepository) {

        this.bookingRepository = bookingRepository;
        this.bookingSeatRepository = bookingSeatRepository;
        this.matchRepository = matchRepository;
        this.matchSeatRepository = matchSeatRepository;
    }

    @Transactional
    public BookingResponse createBooking(Long matchId,
                                         BookingRequest request) {

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Match not found with id: " + matchId
                ));

        // Check for duplicate seat IDs
        if (request.getSeatIds().size()
                != request.getSeatIds().stream().distinct().count()) {

            throw new DuplicateSeatException(
                    "Duplicate seat IDs are not allowed"
            );
        }

        List<MatchSeat> seats = new ArrayList<>();

        // First load and validate all seats
        for (Long seatId : request.getSeatIds()) {

            MatchSeat seat = matchSeatRepository.findById(seatId)
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Seat not found with id: " + seatId
                    ));

            if (!seat.getMatch().getId().equals(matchId)) {

                throw new SeatDoesNotBelongToMatchException(
                        "Seat " + seatId
                                + " does not belong to match " + matchId
                );
            }

            if (seat.getStatus() == SeatStatus.BOOKED) {

                throw new SeatAlreadyBookedException(
                        "Seat " + seat.getSeatNumber()
                                + " is already booked"
                );
            }

            seats.add(seat);
        }

        // Calculate total amount
        BigDecimal totalAmount = seats.stream()
                .map(MatchSeat::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Atomically book every seat
        for (MatchSeat seat : seats) {

            int updatedRows = matchSeatRepository.bookSeat(
                    seat.getId(),
                    seat.getVersion()
            );

            if (updatedRows == 0) {

                throw new SeatAlreadyBookedException(
                        "Seat " + seat.getSeatNumber()
                                + " is already booked"
                );
            }
        }

        // Create booking only after all seats are successfully reserved
        Booking booking = new Booking(
                match,
                request.getCustomerName(),
                totalAmount,
                BookingStatus.CONFIRMED,
                LocalDateTime.now()
        );

        Booking savedBooking = bookingRepository.save(booking);

        List<String> seatNumbers = new ArrayList<>();

        for (MatchSeat seat : seats) {

            BookingSeat bookingSeat =
                    new BookingSeat(savedBooking, seat);

            bookingSeatRepository.save(bookingSeat);

            seatNumbers.add(seat.getSeatNumber());
        }

        return new BookingResponse(
                savedBooking.getId(),
                match.getId(),
                savedBooking.getCustomerName(),
                seatNumbers,
                savedBooking.getTotalAmount(),
                savedBooking.getStatus(),
                savedBooking.getBookingTime()
        );
    }

    public BookingResponse getBookingById(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + bookingId
                ));

        // Fetch only seats belonging to this booking
        List<String> seatNumbers = bookingSeatRepository
                .findByBookingId(bookingId)
                .stream()
                .map(bookingSeat ->
                        bookingSeat.getMatchSeat().getSeatNumber())
                .toList();

        return new BookingResponse(
                booking.getId(),
                booking.getMatch().getId(),
                booking.getCustomerName(),
                seatNumbers,
                booking.getTotalAmount(),
                booking.getStatus(),
                booking.getBookingTime()
        );
    }
    
    
    
    @Transactional
    public void cancelBooking(Long bookingId) {

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + bookingId
                ));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingAlreadyCancelledException(
                    "Booking is already cancelled"
            );
        }

        List<BookingSeat> bookingSeats =
                bookingSeatRepository.findByBookingId(bookingId);

        for (BookingSeat bookingSeat : bookingSeats) {

            MatchSeat seat = bookingSeat.getMatchSeat();

            seat.setStatus(SeatStatus.AVAILABLE);

            matchSeatRepository.save(seat);
        }

        booking.setStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);
    }
}