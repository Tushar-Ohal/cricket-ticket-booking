package com.tushar.cricketbooking.service;

import com.tushar.cricketbooking.dto.SeatResponse;
import com.tushar.cricketbooking.entity.Match;
import com.tushar.cricketbooking.entity.MatchSeat;
import com.tushar.cricketbooking.enums.SeatStatus;
import com.tushar.cricketbooking.exception.ResourceNotFoundException;
import com.tushar.cricketbooking.repository.MatchRepository;
import com.tushar.cricketbooking.repository.MatchSeatRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MatchSeatService {

    private final MatchSeatRepository matchSeatRepository;
    private final MatchRepository matchRepository;

    public MatchSeatService(MatchSeatRepository matchSeatRepository,
                             MatchRepository matchRepository) {

        this.matchSeatRepository = matchSeatRepository;
        this.matchRepository = matchRepository;
    }

    public SeatResponse createSeat(Long matchId,
                                   String seatNumber,
                                   BigDecimal price) {

        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Match not found with id: " + matchId
                ));

        MatchSeat seat = new MatchSeat(
                match,
                seatNumber,
                price,
                SeatStatus.AVAILABLE
        );

        MatchSeat savedSeat = matchSeatRepository.save(seat);

        return toResponse(savedSeat);
    }

    public List<SeatResponse> getSeatsByMatch(Long matchId) {

        if (!matchRepository.existsById(matchId)) {
            throw new ResourceNotFoundException(
                    "Match not found with id: " + matchId
            );
        }

        return matchSeatRepository.findByMatchId(matchId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private SeatResponse toResponse(MatchSeat seat) {

        return new SeatResponse(
                seat.getId(),
                seat.getMatch().getId(),
                seat.getSeatNumber(),
                seat.getPrice(),
                seat.getStatus()
        );
    }
}