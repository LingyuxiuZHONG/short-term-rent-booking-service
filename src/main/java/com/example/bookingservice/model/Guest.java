package com.example.bookingservice.model;


import lombok.Data;

@Data
public class Guest {
    private Long id;
    private Long bookingId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
