package com.mock1.flight.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Flight {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long flightId;
    @Pattern(regexp = "^[A-Z0-9]{1,6}$")
    @Column(nullable = false, length = 6, unique = true)
    String flightNumber;
    @Column(nullable = false)
    String aerolineName;
    @NotNull
    @Column(nullable = false)
    LocalDateTime departureTime;
    @NotNull
    @Column(nullable = false)
    LocalDateTime arrivalTime;
    @Positive
    @Column(nullable = false)
    int totalSeats;
    @Min(0)
    @Column(nullable = false)
    int availableSeats;
}