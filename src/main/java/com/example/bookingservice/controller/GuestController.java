package com.example.bookingservice.controller;


import com.example.bookingservice.dto.BookingCreateDTO;
import com.example.bookingservice.dto.GuestCreateDTO;
import com.example.bookingservice.service.GuestService;
import com.example.common.ApiResponse;
import com.example.feignapi.vo.BookingVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;


    @PostMapping()
    public ResponseEntity<ApiResponse<String>> createGuest(@Valid @RequestBody GuestCreateDTO guestCreateDTO) {
        guestService.createGuest(guestCreateDTO);
        return ResponseEntity.ok(ApiResponse.success("入住人创建成功"));
    }

}
