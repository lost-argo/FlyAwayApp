package com.mock1.booking.infrastructure;

import com.mock1.booking.domain.Booking;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query
    List<Booking> findOverlapingBookings(
            @Param("userId") Long userId,
            @Param("departureTime") LocalDateTime departureTime,
            @Param("arrivalTime") LocalDateTime arrivalTime
    );
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Booking> findByIdAndCustomerId(Long id, Long customerId);
}
