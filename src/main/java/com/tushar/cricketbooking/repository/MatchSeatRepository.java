package com.tushar.cricketbooking.repository;

import com.tushar.cricketbooking.entity.MatchSeat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MatchSeatRepository extends JpaRepository<MatchSeat, Long> {

    List<MatchSeat> findByMatchId(Long matchId);

    @Modifying
    @Query("""
        UPDATE MatchSeat s
        SET s.status = com.tushar.cricketbooking.enums.SeatStatus.BOOKED,
            s.version = s.version + 1
        WHERE s.id = :seatId
          AND s.status = com.tushar.cricketbooking.enums.SeatStatus.AVAILABLE
          AND s.version = :version
    """)
    int bookSeat(
            @Param("seatId") Long seatId,
            @Param("version") Long version
    );
}