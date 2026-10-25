package com.tushar.cricketbooking.integration;

import com.tushar.cricketbooking.dto.BookingRequest;
import com.tushar.cricketbooking.dto.BookingResponse;
import com.tushar.cricketbooking.service.BookingService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingConcurrencyTest {

    @Autowired
    private BookingService bookingService;

    @Test
    void testConcurrentBooking() throws Exception {

        // Same seat will be requested by both users
        Long matchId = 1L;
        Long seatId = 5L;

        BookingRequest requestA = new BookingRequest();
        requestA.setCustomerName("User A");
        requestA.setSeatIds(List.of(seatId));

        BookingRequest requestB = new BookingRequest();
        requestB.setCustomerName("User B");
        requestB.setSeatIds(List.of(seatId));

        // Two threads
        ExecutorService executor = Executors.newFixedThreadPool(2);

        // Makes both threads start the booking at nearly the same time
        CountDownLatch startLatch = new CountDownLatch(1);

        Future<?> futureA = executor.submit(() -> {

            try {
                startLatch.await();

                BookingResponse response =
                        bookingService.createBooking(matchId, requestA);

                System.out.println(
                        "User A booking successful: "
                        + response.getBookingId()
                );

            } catch (Exception e) {

                System.out.println(
                        "User A failed: "
                        + e.getClass().getSimpleName()
                        + " - "
                        + e.getMessage()
                );

                throw new RuntimeException(e);
            }
        });

        Future<?> futureB = executor.submit(() -> {

            try {
                startLatch.await();

                BookingResponse response =
                        bookingService.createBooking(matchId, requestB);

                System.out.println(
                        "User B booking successful: "
                        + response.getBookingId()
                );

            } catch (Exception e) {

                System.out.println(
                        "User B failed: "
                        + e.getClass().getSimpleName()
                        + " - "
                        + e.getMessage()
                );

                throw new RuntimeException(e);
            }
        });

        // Release both threads
        startLatch.countDown();

        // Wait for both threads
        boolean userASuccess = false;
        boolean userBSuccess = false;

        try {
            futureA.get();
            userASuccess = true;
        } catch (Exception e) {
            System.out.println("User A transaction failed.");
        }

        try {
            futureB.get();
            userBSuccess = true;
        } catch (Exception e) {
            System.out.println("User B transaction failed.");
        }

        executor.shutdown();

        // Exactly ONE user should succeed
        assertTrue(
                userASuccess ^ userBSuccess,
                "Exactly one user should successfully book the seat"
        );
    }
}