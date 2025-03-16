package com.example.bookingservice.controller;


import com.example.bookingservice.dto.BookingCreateDTO;
import com.example.bookingservice.service.BookingService;
import com.example.common.ApiResponse;
import com.example.feignapi.vo.BookingVO;
import com.example.feignapi.dto.CheckDateAvailabilityDTO;
import com.example.feignapi.dto.UpdateBookingPaymentDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping()
    public ResponseEntity<ApiResponse<BookingVO>> createBooking(@Valid @RequestBody BookingCreateDTO dto) {
        BookingVO bookingVO = bookingService.createBooking(dto);
        return ResponseEntity.ok(ApiResponse.success("预定创建成功", bookingVO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingVO>> getBookingById(@PathVariable Long id) {
        BookingVO bookingVO = bookingService.getBookingById(id);
        return ResponseEntity.ok(ApiResponse.success("查询成功",bookingVO));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<String>> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok(ApiResponse.success("取消成功"));
    }


    @GetMapping("/guest/{guestId}")
    public ResponseEntity<ApiResponse<List<BookingVO>>> getGuestBookings(@PathVariable Long guestId) {
        List<BookingVO> bookings = bookingService.getBookingsByGuestId(guestId);
        return ResponseEntity.ok(ApiResponse.success("查询成功",bookings));
    }

    @GetMapping("/host/{hostId}")
    public ResponseEntity<ApiResponse<List<BookingVO>>> getHostBookings(@PathVariable Long hostId) {
        List<BookingVO> bookings = bookingService.getBookingsByHostId(hostId);
        return ResponseEntity.ok(ApiResponse.success("查询成功", bookings));
    }

    @PutMapping("/updatePayment")
    public ResponseEntity<ApiResponse<String>> updateBookingPayment(@RequestBody UpdateBookingPaymentDTO updateBookingPaymentDTO){
        bookingService.updatePaymentInfo(updateBookingPaymentDTO);
        return ResponseEntity.ok(ApiResponse.success("更新成功"));
    }

    @PostMapping("/checkDateAvailability")
    public ResponseEntity<ApiResponse<List<Long>>> checkDateAvailability(@RequestBody CheckDateAvailabilityDTO checkDateAvailabilityDTO){
        List<Long> listingIds = bookingService.checkDateAvailability(checkDateAvailabilityDTO);
        return ResponseEntity.ok(ApiResponse.success("查询成功", listingIds));
    }

}
