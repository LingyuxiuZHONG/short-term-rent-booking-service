package com.example.bookingservice.mapper;


import com.example.bookingservice.model.Booking;
import com.example.feignapi.dto.CheckDateAvailabilityDTO;
import com.example.feignapi.dto.UpdateBookingPaymentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BookingMapper {

    int countConflictingBookings(Long listingId, LocalDate startDate, LocalDate endDate);

    void insert(Booking booking);

    Booking getBookingById(Long id);

    void updateStatusToCancelled(Long id, int status, LocalDateTime cancelledAt);

    List<Booking> getBookingsByGuestId(Long guestId);

    List<Booking> getBookingsByHostId(Long hostId);

    void updatePaymentInfo(Booking updateBookingPaymentDTO);

    List<Long> dateIsOccupied(CheckDateAvailabilityDTO checkDateAvailabilityDTO);
}
