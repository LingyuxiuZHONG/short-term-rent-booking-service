package com.example.bookingservice.controller;


import com.example.bookingservice.dto.BookingCancelDTO;
import com.example.bookingservice.dto.BookingCreateDTO;
import com.example.bookingservice.service.BookingService;
import com.example.common.ApiResponse;
import com.example.feignapi.vo.BookingAndPaymentInfo;
import com.example.feignapi.vo.BookingCard;
import com.example.feignapi.vo.BookingUpdateAfterPayDTO;
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
    public ResponseEntity<ApiResponse<Long>> createBooking(@Valid @RequestBody BookingCreateDTO dto) {
        Long bookingId = bookingService.createBooking(dto);
        return ResponseEntity.ok(ApiResponse.success("预定创建成功", bookingId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookingVO>> getBookingById(@PathVariable Long id) {
        BookingVO bookingVO = bookingService.getBookingById(id);
        return ResponseEntity.ok(ApiResponse.success("查询成功",bookingVO));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<BookingAndPaymentInfo>> cancelBooking(@PathVariable Long id, @RequestBody BookingCancelDTO bookingCancelDTO) {
        BookingAndPaymentInfo bookingAndPaymentInfo = bookingService.cancelBooking(id, bookingCancelDTO);
        return ResponseEntity.ok(ApiResponse.success("取消成功", bookingAndPaymentInfo));
    }


    @GetMapping("/bookingUser/{bookingUserId}")
    public ResponseEntity<ApiResponse<List<BookingCard>>> getBookingsByGuestId(@PathVariable Long bookingUserId) {
        List<BookingCard> bookings = bookingService.getBookingsByBookingUserId(bookingUserId);
        return ResponseEntity.ok(ApiResponse.success("查询成功",bookings));
    }

    @GetMapping("/listing/{listingId}")
    ResponseEntity<ApiResponse<List<BookingVO>>> getBookingsByListingId(@PathVariable("listingId") Long listingId){
        List<BookingVO> bookings = bookingService.getBookingsByListingId(listingId);
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

    @PutMapping("/{id}/status")
    ResponseEntity<ApiResponse<String>> updateBookingStatus(@PathVariable Long id,@RequestParam int code){
        bookingService.updateBookingStatus(id,code);
        return ResponseEntity.ok(ApiResponse.success("更新成功"));
    }

    @PutMapping("/{id}/success")
    ResponseEntity<ApiResponse<String>> updateBookingAfterPay(@PathVariable Long id, @RequestBody BookingUpdateAfterPayDTO dto){
        bookingService.updateBookingAfterPay(id, dto);
        return ResponseEntity.ok(ApiResponse.success("更新成功"));
    }

}
