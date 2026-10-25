package com.tushar.cricketbooking.repository;

import com.tushar.cricketbooking.entity.BookingSeat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {
	
	List<BookingSeat> findByBookingId(Long bookingId);
}