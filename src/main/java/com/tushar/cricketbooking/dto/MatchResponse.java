package com.tushar.cricketbooking.dto;

import com.tushar.cricketbooking.enums.MatchStatus;

import java.time.LocalDateTime;

public class MatchResponse {

    private Long id;
    private String teamA;
    private String teamB;
    private LocalDateTime matchDateTime;
    private String venue;
    private MatchStatus status;

    public MatchResponse() {
    }

    public MatchResponse(Long id, String teamA, String teamB,
                          LocalDateTime matchDateTime,
                          String venue, MatchStatus status) {
        this.id = id;
        this.teamA = teamA;
        this.teamB = teamB;
        this.matchDateTime = matchDateTime;
        this.venue = venue;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTeamA() {
        return teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public LocalDateTime getMatchDateTime() {
        return matchDateTime;
    }

    public String getVenue() {
        return venue;
    }

    public MatchStatus getStatus() {
        return status;
    }
}