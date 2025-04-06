package com.example.bookingservice.mapper;


import com.example.bookingservice.model.Booking;
import com.example.feignapi.dto.CheckDateAvailabilityDTO;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface BookingMapper {

    int countConflictingBookings(Long listingId, LocalDate startDate, LocalDate endDate);

    void insert(Booking booking);

    Booking getBookingById(Long id);

    void updateStatusToCancelled(Long id, int status, LocalDateTime cancelledAt, String cancelReason);

    List<Booking> getBookingsByBookingUserId(Long guestId);

    List<Booking> getBookingsByHostId(Long hostId);

    void updatePaymentInfo(Booking updateBookingPaymentDTO);

    List<Long> dateIsOccupied(CheckDateAvailabilityDTO checkDateAvailabilityDTO);

    List<Booking> getBookingsByListingId(Long listingId);

    void updateBookingStatus(Long id, int code);

    void updateBookingAfterPay(Long id, int code, String tradeNo, BigDecimal paidAmount);

    void updateCancelInfo(Booking booking);
}
