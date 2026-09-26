package com.mock1.booking.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BookFlightRequest (
        @NotNull Long id,
        @NotNull Long customerId,
        @NotNull LocalDateTime bookingDate,
        @NotNull String customerFullName) {
}
