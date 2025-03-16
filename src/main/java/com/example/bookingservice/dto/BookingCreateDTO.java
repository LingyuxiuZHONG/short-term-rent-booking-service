package com.example.bookingservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BookingCreateDTO {
    @NotNull(message = "房源ID不能为空")
    private Long listingId;

    @NotNull(message = "房客ID不能为空")
    private Long guestId;

    @NotNull(message = "房东ID不能为空")
    private Long hostId;

    @NotNull(message = "入住日期不能为空")
    @FutureOrPresent(message = "入住日期不能是过去时间")
    private LocalDate startDate;

    @NotNull(message = "退房日期不能为空")
    @Future(message = "退房日期必须晚于今天")
    private LocalDate endDate;

    @NotNull(message = "入住人数不能为空")
    @Min(value = 1, message = "至少入住1人")
    private Integer guestCount;

    @NotNull(message = "订单总金额不能为空")
    @DecimalMin(value = "0.00", inclusive = true, message = "订单总金额不能小于0")
    private BigDecimal totalAmount;


    @DecimalMin(value = "0.00", inclusive = true, message = "优惠金额不能小于0")
    private BigDecimal discountAmount;
}
