package com.tushar.cricketbooking.repository;

import com.tushar.cricketbooking.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {

}