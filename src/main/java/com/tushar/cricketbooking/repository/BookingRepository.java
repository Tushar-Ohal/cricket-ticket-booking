package com.tushar.cricketbooking.repository;

import com.tushar.cricketbooking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
