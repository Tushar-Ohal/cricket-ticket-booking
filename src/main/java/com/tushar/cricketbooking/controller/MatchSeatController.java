package com.tushar.cricketbooking.controller;

import com.tushar.cricketbooking.dto.SeatResponse;
import com.tushar.cricketbooking.service.MatchSeatService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchSeatController {

    private final MatchSeatService matchSeatService;

    public MatchSeatController(MatchSeatService matchSeatService) {
        this.matchSeatService = matchSeatService;
    }

    @PostMapping("/{matchId}/seats")
    public ResponseEntity<SeatResponse> createSeat(
            @PathVariable Long matchId,
            @RequestParam String seatNumber,
            @RequestParam BigDecimal price) {

        SeatResponse response = matchSeatService.createSeat(
                matchId,
                seatNumber,
                price
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{matchId}/seats")
    public ResponseEntity<List<SeatResponse>> getSeatsByMatch(
            @PathVariable Long matchId) {

        List<SeatResponse> response =
                matchSeatService.getSeatsByMatch(matchId);

        return ResponseEntity.ok(response);
    }
}