package com.mock1.booking.dto;

import java.time.LocalDateTime;

public record BookFlightResponse(
        Long bookingId,
        Long flightId,
        String flightNumber,
        String customerFullName,
        LocalDateTime bookingDate,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime) {}
