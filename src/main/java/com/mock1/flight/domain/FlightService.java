package com.mock1.flight.domain;

import com.mock1.flight.infrastructure.FlightRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id).orElseThrow(() -> new RuntimeException("Flight with id " + id + " not found"));
    }

    public List<Flight> searchFlights(String flightNumber, String airlineName, LocalDateTime departureDate, LocalDateTime arrivalDate) {
        if (!departureDate.isBefore(arrivalDate)) {
            throw new RuntimeException("Departure date should be before arrival date");
        }
        return flightRepository.searchFlights(flightNumber, airlineName, departureDate, arrivalDate);
    }

    public void addFlight(Flight newFlight) {
        if (!newFlight.getDepartureTime().isBefore(newFlight.getArrivalTime())) {
            throw new IllegalArgumentException("Departure and arrival time don't match");
        } else if (newFlight.getTotalSeats() < newFlight.getAvailableSeats()){
            throw new IllegalArgumentException("Available and total seats don't match");
        }
        flightRepository.save(newFlight);
    }
}