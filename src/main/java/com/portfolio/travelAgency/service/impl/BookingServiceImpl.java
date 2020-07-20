package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.repository.BookingRepository;
import com.portfolio.travelAgency.service.dto.BookingDTO;
import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.mapper.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public void createNewBooking(BookingDTO bookingDTO) {
        bookingRepository.save(bookingMapper.toEntity(bookingDTO));
    }

    @Override
    public boolean matchDateArrival(String date) {
        LocalDate choseDate = LocalDate.parse(date);

        if (choseDate.isBefore(LocalDate.now())){
            return false;
        }else return true;
    }
}
