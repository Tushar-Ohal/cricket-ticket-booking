package com.tushar.cricketbooking.service;

import com.tushar.cricketbooking.dto.MatchRequest;
import com.tushar.cricketbooking.dto.MatchResponse;
import com.tushar.cricketbooking.entity.Match;
import com.tushar.cricketbooking.exception.ResourceNotFoundException;
import com.tushar.cricketbooking.repository.MatchRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public MatchResponse createMatch(MatchRequest request) {

        Match match = new Match(
                request.getTeamA(),
                request.getTeamB(),
                request.getMatchDateTime(),
                request.getVenue(),
                request.getStatus()
        );

        Match savedMatch = matchRepository.save(match);

        return toResponse(savedMatch);
    }

    public List<MatchResponse> getAllMatches() {

        return matchRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private MatchResponse toResponse(Match match) {

        return new MatchResponse(
                match.getId(),
                match.getTeamA(),
                match.getTeamB(),
                match.getMatchDateTime(),
                match.getVenue(),
                match.getStatus()
        );
    }
    
    
    
    public MatchResponse getMatchById(Long id) {

        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + id));

        return toResponse(match);
    }
    
    
    public Page<MatchResponse> getMatches(Pageable pageable) {

        return matchRepository.findAll(pageable)
                .map(this::toResponse);
    }
}