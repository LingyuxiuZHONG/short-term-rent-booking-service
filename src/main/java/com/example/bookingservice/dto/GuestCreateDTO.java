package com.example.bookingservice.dto;

import lombok.Data;

@Data
public class GuestCreateDTO {
    private Long bookingId;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Integer guestCount;
}
