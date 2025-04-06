package com.example.bookingservice.service.impl;

import com.example.bookingservice.dto.GuestCreateDTO;
import com.example.bookingservice.mapper.GuestMapper;
import com.example.bookingservice.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestMapper guestMapper;


    @Override
    public void createGuest(GuestCreateDTO guestCreateDTO) {
        guestMapper.createGuest(guestCreateDTO);
    }
}
