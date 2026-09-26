package com.mock1.flight.application;

import com.mock1.flight.domain.Flight;
import com.mock1.flight.domain.FlightService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
    @RequestMapping("/flights")
    public class FlightController {
        private final FlightService flightService;

        public FlightController(FlightService flightService) {
            this.flightService = flightService;
        }

        @GetMapping
        public ResponseEntity<List<Flight>> getAllFlights() {
            return ResponseEntity.ok(flightService.getAllFlights());
        }

        @PostMapping
        public ResponseEntity<Void> newFlight(@Valid @RequestBody Flight newFlight) {
            flightService.addFlight(newFlight);
            return ResponseEntity.status(HttpStatusCode.valueOf(201)).build();
        }

        public ResponseEntity<List<Flight>> searchFlights(
                @RequestParam(required = false) String flightNumber, String aerolineName,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departureTime, LocalDateTime arrivalTime
        ) {
            List<Flight> results = flightService.searchFlights(flightNumber,aerolineName,departureTime,arrivalTime);
            return ResponseEntity.ok(results);
        }
    }