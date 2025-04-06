package com.example.bookingservice.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class BookingCancelDTO {
    private String cancelReason;
    private BigDecimal refundAmount;
    private Long cancelledBy;
}
