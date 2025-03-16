package com.example.bookingservice.service.impl;

import com.example.bookingservice.dto.BookingCreateDTO;
import com.example.bookingservice.mapper.BookingMapper;
import com.example.bookingservice.model.Booking;
import com.example.common.dto.BookingStatus;
import com.example.bookingservice.service.BookingService;
import com.example.common.exception.BusinessException;
import com.example.feignapi.dto.CheckDateAvailabilityDTO;
import com.example.feignapi.dto.UpdateBookingPaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.example.feignapi.vo.BookingVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {

    private final BookingMapper bookingMapper;

    @Override
    public BookingVO createBooking(BookingCreateDTO bookingCreateDTO) {
        log.info("创建预订开始，房源ID：{}，入住日期：{}，退房日期：{}", bookingCreateDTO.getListingId(), bookingCreateDTO.getStartDate(), bookingCreateDTO.getEndDate());

        // 检查房源是否可用
        countConflictingBookings(bookingCreateDTO.getListingId(), bookingCreateDTO.getStartDate(), bookingCreateDTO.getEndDate());
        log.info("房源可用性检查通过");

        // 创建预订
        Booking booking = new Booking();
        BeanUtils.copyProperties(bookingCreateDTO, booking);
        bookingMapper.insert(booking);
        log.info("预订创建成功，预订ID：{}", booking.getId());

        // 查询完整的预订信息
        Booking fullBooking = bookingMapper.getBookingById(booking.getId());
        return convertToBookingVO(fullBooking);
    }

    @Override
    public BookingVO getBookingById(Long id) {
        log.info("查询预订，预订ID：{}", id);

        Booking booking = bookingMapper.getBookingById(id);
        if (booking == null) {
            log.warn("预订不存在，预订ID：{}", id);
            throw new BusinessException("预订不存在");
        }

        log.info("预订查询成功，预订ID：{}", id);
        return convertToBookingVO(booking);
    }

    @Override
    public void cancelBooking(Long id) {
        log.info("取消预订开始，预订ID：{}", id);

        // 判断订单是否存在
        Booking booking = bookingMapper.getBookingById(id);
        if (booking == null) {
            log.warn("订单不存在，预订ID：{}", id);
            throw new BusinessException("订单不存在");
        }

        // 校验当前订单状态
        Integer status = booking.getStatus();
        if (status.equals(BookingStatus.CANCELLED.getCode())) {
            log.warn("订单已取消，不能重复操作，预订ID：{}", id);
            throw new BusinessException("订单已取消，不能重复操作");
        }
        if (status.equals(BookingStatus.COMPLETED.getCode())) {
            log.warn("订单已完成，不能取消，预订ID：{}", id);
            throw new BusinessException("订单已完成，不能取消");
        }
        if (status.equals(BookingStatus.REFUNDED.getCode())) {
            log.warn("订单已退款，不能取消，预订ID：{}", id);
            throw new BusinessException("订单已退款，不能取消");
        }

        // 更新订单状态为取消
        bookingMapper.updateStatusToCancelled(booking.getId(), BookingStatus.CANCELLED.getCode(), LocalDateTime.now());
        log.info("订单已取消，预订ID：{}", id);

        // 如果已经支付，则调用支付微服务进行退款
        if (booking.getPaidAmount() != null && booking.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
            // TODO 调用支付微服务，退款
            log.info("订单已支付，正在进行退款处理，预订ID：{}", id);
        }
    }

    @Override
    public List<BookingVO> getBookingsByGuestId(Long guestId) {
        log.info("查询客人预订记录，客人ID：{}", guestId);

        List<Booking> bookings = bookingMapper.getBookingsByGuestId(guestId);
        log.info("查询到预订记录数量：{}", bookings.size());

        return bookings.stream()
                .map(this::convertToBookingVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingVO> getBookingsByHostId(Long hostId) {
        log.info("查询房东预订记录，房东ID：{}", hostId);

        List<Booking> bookings = bookingMapper.getBookingsByHostId(hostId);
        log.info("查询到预订记录数量：{}", bookings.size());

        return bookings.stream()
                .map(this::convertToBookingVO)
                .collect(Collectors.toList());
    }

    @Override
    public void updatePaymentInfo(UpdateBookingPaymentDTO updateBookingPaymentDTO) {
        Long id = updateBookingPaymentDTO.getBookingId();
        log.info("更新预订支付信息，预订ID：{}", id);

        Booking booking = bookingMapper.getBookingById(id);
        if (booking == null) {
            log.warn("预订不存在，预订ID：{}", id);
            throw new BusinessException("订单不存在");
        }

        BeanUtils.copyProperties(updateBookingPaymentDTO, booking);

        bookingMapper.updatePaymentInfo(booking);
        log.info("支付信息更新成功，预订ID：{}", id);
    }

    @Override
    public List<Long> checkDateAvailability(CheckDateAvailabilityDTO checkDateAvailabilityDTO) {
        // 获取日期被占用的房源 ID 列表
        List<Long> dateIsOccupiedListingIds = bookingMapper.dateIsOccupied(checkDateAvailabilityDTO);

        // 获取传入的房源 ID 列表
        List<Long> listingIds = checkDateAvailabilityDTO.getListingIds();

        // 从 listingIds 中移除那些被占用的房源 ID
        listingIds.removeAll(dateIsOccupiedListingIds);

        // 返回剩余的空闲房源 ID 列表
        return listingIds;
    }



    private void countConflictingBookings(Long listingId, LocalDate startDate, LocalDate endDate) {
        int conflictCount = bookingMapper.countConflictingBookings(listingId, startDate, endDate);
        if (conflictCount > 0) {
            throw new BusinessException("当前日期范围内，房源已被预订，请选择其他日期");
        }
    }

    private BookingVO convertToBookingVO(Booking booking) {
        BookingVO bookingVO = new BookingVO();
        BeanUtils.copyProperties(booking,bookingVO);
        return bookingVO;
    }



}
