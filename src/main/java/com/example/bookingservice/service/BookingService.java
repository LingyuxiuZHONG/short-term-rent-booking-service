package com.example.bookingservice.service;

import com.example.bookingservice.dto.BookingCreateDTO;
import com.example.feignapi.dto.CheckDateAvailabilityDTO;
import com.example.feignapi.dto.UpdateBookingPaymentDTO;
import com.example.feignapi.vo.BookingVO;

import java.util.List;

public interface BookingService {
    BookingVO createBooking(BookingCreateDTO bookingCreateDTO);

    BookingVO getBookingById(Long id);

    void cancelBooking(Long id);

    List<BookingVO> getBookingsByGuestId(Long guestId);

    List<BookingVO> getBookingsByHostId(Long hostId);

    void updatePaymentInfo(UpdateBookingPaymentDTO updateBookingPaymentDTO);

    List<Long> checkDateAvailability(CheckDateAvailabilityDTO checkDateAvailabilityDTO);
}
