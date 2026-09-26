package com.mock1.booking.application;

import com.mock1.booking.domain.BookingService;
import com.mock1.booking.dto.BookFlightRequest;
import com.mock1.booking.dto.BookFlightResponse;
import com.mock1.user.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/flights")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public ResponseEntity<BookFlightResponse> bookFlight(
            @Valid @RequestBody BookFlightRequest request,
            @AuthenticationPrincipal User currentUser) {

        BookFlightResponse response = bookingService.bookFlight(request.id(), currentUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookFlightResponse> getBooking(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser) {

        return ResponseEntity.ok(bookingService.getBooking(id, currentUser));
    }
}