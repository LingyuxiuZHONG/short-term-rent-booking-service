package com.example.bookingservice.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class Booking {
    private Long id;  // 预订ID
    private Long listingId;  // 房源ID
    private Long bookingUserId;  // 预订人ID
    private Long hostId;  // 房东ID
    private LocalDate startDate;  // 入住日期
    private LocalDate endDate;  // 退房日期
    private Integer guestCount;  // 入住人数
    private BigDecimal totalAmount;  // 订单总金额
    private BigDecimal paidAmount;  // 实际支付金额
    private BigDecimal discountAmount;  // 优惠金额
    private Integer status;  // 订单状态（待支付、已支付、已完成等）
    private String paymentTransactionId;  // 支付流水号（第三方支付平台返回）
    private LocalDateTime createdAt;  // 创建时间
    private LocalDateTime updatedAt;  // 更新时间
    private LocalDateTime cancelledAt;  // 取消时间
    private LocalDateTime completedAt;  // 订单完成时间
    private String refundTransactionId;
    private Integer cancelledBy;
    private String cancelReason;
    private Integer cancelPolicy;
}
