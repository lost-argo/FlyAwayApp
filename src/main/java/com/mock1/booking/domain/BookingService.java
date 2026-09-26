package com.mock1.booking.domain;

import com.mock1.booking.dto.BookFlightResponse;
import com.mock1.booking.infrastructure.BookingRepository;
import com.mock1.flight.domain.Flight;
import com.mock1.flight.infrastructure.FlightRepository;
import com.mock1.user.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    public BookingService(BookingRepository bookingRepository, FlightRepository flightRepository) {
        this.bookingRepository = bookingRepository;
        this.flightRepository = flightRepository;
    }

    @Transactional
    public BookFlightResponse bookFlight(Long flightId, User currentUser) {

        Flight flight = flightRepository.findById(flightId)
                .orElseThrow(() -> new RuntimeException("Flight with id " + flightId + " not found"));

        LocalDateTime now = LocalDateTime.now();
        if (flight.getDepartureTime().isBefore(now)) {
            throw new IllegalStateException("Cannot book a flight that has already departed or is in transit");
        }

        if (flight.getAvailableSeats() <= 0) {
            throw new IllegalStateException("No available seats on this flight");
        }

        List<Booking> overlaps = bookingRepository.findOverlapingBookings(
                currentUser.getUserId(),
                flight.getDepartureTime(),
                flight.getArrivalTime()
        );
        if (!overlaps.isEmpty()) {
            throw new IllegalStateException("You already have a booking overlapping this time range");
        }

        flight.setAvailableSeats(flight.getAvailableSeats() - 1);
        flightRepository.save(flight);

        Booking booking = new Booking();
        booking.setFlight(flight);
        booking.setCustomer(currentUser);
        booking.setBookingDate(now);
        booking.setCustomerFullName(
                currentUser.getFirstName() + " " + currentUser.getLastName()
        );

        Booking saved = bookingRepository.save(booking);

        return toResponse(saved);
    }

    @Transactional
    public BookFlightResponse getBooking(Long bookingId, User currentUser) {
        Booking booking = bookingRepository
                .findByIdAndCustomerId(bookingId, currentUser.getUserId())
                .orElseThrow(() -> new RuntimeException("Booking with id " + bookingId + " not found"));

        return toResponse(booking);
    }

    private BookFlightResponse toResponse(Booking b) {
        return new BookFlightResponse(
                b.getBookingId(),
                b.getFlight().getFlightId(),
                b.getFlight().getFlightNumber(),
                b.getCustomerFullName(),
                b.getBookingDate(),
                b.getFlight().getDepartureTime(),
                b.getFlight().getArrivalTime()
        );
    }
}