package com.example.bookingservice.service;

import com.example.bookingservice.dto.BookingCancelDTO;
import com.example.bookingservice.dto.BookingCreateDTO;
import com.example.feignapi.dto.CheckDateAvailabilityDTO;
import com.example.feignapi.dto.UpdateBookingPaymentDTO;
import com.example.feignapi.vo.BookingAndPaymentInfo;
import com.example.feignapi.vo.BookingCard;
import com.example.feignapi.vo.BookingUpdateAfterPayDTO;
import com.example.feignapi.vo.BookingVO;

import java.util.List;

public interface BookingService {
    Long createBooking(BookingCreateDTO bookingCreateDTO);

    BookingVO getBookingById(Long id);

    BookingAndPaymentInfo cancelBooking(Long id, BookingCancelDTO bookingCancelDTO);

    List<BookingCard> getBookingsByBookingUserId(Long guestId);

    List<BookingVO> getBookingsByHostId(Long hostId);

    void updatePaymentInfo(UpdateBookingPaymentDTO updateBookingPaymentDTO);

    List<Long> checkDateAvailability(CheckDateAvailabilityDTO checkDateAvailabilityDTO);

    List<BookingVO> getBookingsByListingId(Long listingId);

    void updateBookingStatus(Long id, int code);

    void updateBookingAfterPay(Long id, BookingUpdateAfterPayDTO dto);
}
