package com.example.bookingservice.mapper;


import com.example.bookingservice.dto.GuestCreateDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GuestMapper {
    void createGuest(GuestCreateDTO guestCreateDTO);

}
