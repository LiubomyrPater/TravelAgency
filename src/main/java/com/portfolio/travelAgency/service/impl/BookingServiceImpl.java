package com.portfolio.travelAgency.service.impl;

import com.portfolio.travelAgency.entity.Booking;
import com.portfolio.travelAgency.entity.BookingsArchived;
import com.portfolio.travelAgency.repository.ArchivedBookingRepository;
import com.portfolio.travelAgency.repository.BookingRepository;
import com.portfolio.travelAgency.repository.RoomRepository;
import com.portfolio.travelAgency.service.dto.BookingDTO;
import com.portfolio.travelAgency.service.interfaces.BookingService;
import com.portfolio.travelAgency.service.mapper.ArchivedBookingMapper;
import com.portfolio.travelAgency.service.mapper.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final RoomRepository roomRepository;
    private final ArchivedBookingMapper archivedBookingMapper;
    private final ArchivedBookingRepository archivedBookingRepository;


    @Override
    public boolean deleteBookingByID(Long ID) {
        if (bookingRepository.findById(ID).isPresent()){
            bookingRepository.deleteById(ID);
            return !bookingRepository.findById(ID).isPresent();
        }else
            return false;
    }

    @Override
    public List<BookingsArchived> archivedTodayBookings() {
        List<Booking> oldBookings = bookingRepository.findAllByDepartureEquals(LocalDate.now());
        List<BookingsArchived> bookingsArchived = oldBookings
                .stream()
                .map(archivedBookingMapper::toArchived)
                .collect(Collectors.toList());

        bookingRepository.deleteAll(oldBookings);
     return archivedBookingRepository.saveAll(bookingsArchived);
    }

    @Override
    public List<BookingsArchived> archivedOldBookings() {
        List<Booking> oldBookings = bookingRepository.findAllByDepartureBefore(LocalDate.now());
        List<BookingsArchived> bookingsArchived = oldBookings
                .stream()
                .map(archivedBookingMapper::toArchived)
                .collect(Collectors.toList());

        bookingRepository.deleteAll(oldBookings);
        return archivedBookingRepository.saveAll(bookingsArchived);
    }

    @Override
    public List<BookingsArchived> archivedUnpaidBookings() {
        List<Booking> unpaidBookings = bookingRepository.findAllByArrivalEquals(LocalDate.now())
                .stream()
                .filter(x -> !x.isPaid())
                .collect(Collectors.toList());

        List<BookingsArchived> bookingsArchived = unpaidBookings
                .stream()
                .map(archivedBookingMapper::toArchived)
                .collect(Collectors.toList());

        bookingRepository.deleteAll(unpaidBookings);
        return archivedBookingRepository.saveAll(bookingsArchived);
    }

    @Override
    public List<BookingDTO> findUserBookingByID(Long userID) {
        return bookingRepository.findUserBookings(userID)
                .stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDTO> findAllByRoom(Long roomID) {
        return bookingRepository.findByRoom(roomRepository.findById(roomID).get())
                .stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkAvailabilityRooms(Set<Booking> bookingSet , String arrival, String departure) {

        LocalDate arrivalDate = LocalDate.parse(arrival);
        LocalDate departureDate = LocalDate.parse(departure);
        LocalDate today = LocalDate.now().minusDays(1);

        bookingSet = bookingSet.stream()
                .filter(x -> x.getDeparture()
                        .isAfter(today))
                .collect(Collectors.toSet());

        if (bookingSet.size() == 0){
            return  true;
        }else {
             return bookingSet.stream().noneMatch(x -> (
                     (arrivalDate.isEqual(x.getDeparture()) && x.isLateDeparture())
                             || (departureDate.isEqual(x.getArrival()) && x.isEarlyArrival())
                             || (arrivalDate.isBefore(x.getDeparture()) && arrivalDate.isAfter(x.getArrival()))
                             || ((departureDate.isAfter(x.getArrival())) && departureDate.isBefore(x.getDeparture()))
                             || (arrivalDate.isEqual(x.getArrival()) && departureDate.isEqual(x.getDeparture()))
                             || (arrivalDate.isBefore(x.getArrival()) && departureDate.isAfter(x.getDeparture()))
                             || (arrivalDate.isAfter(x.getArrival()) && departureDate.isBefore(x.getDeparture()))
                             || (arrivalDate.isAfter(x.getArrival()) && departureDate.isEqual(x.getDeparture()))
                             || (arrivalDate.isEqual(x.getArrival()) && departureDate.isBefore(x.getDeparture()))
                             || (arrivalDate.isBefore(x.getArrival()) && departureDate.isEqual(x.getDeparture()))
                             || (arrivalDate.isEqual(x.getArrival()) && departureDate.isAfter(x.getDeparture()))
             ));
        }
    }

    @Override
    public List<BookingDTO> findUserBookingsByEmail(String email) {
        return bookingRepository.findUserBookingsByEmail(email)
                .stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void createNewBooking(BookingDTO bookingDTO) {
        bookingRepository.save(bookingMapper.toEntity(bookingDTO));
    }

    @Override
    public boolean matchDateArrival(String date) {
        return !LocalDate.parse(date).isBefore(LocalDate.now());
    }

    @Override
    public boolean matchDateDeparture(String dateArrival, String dateDeparture) {
        return LocalDate.parse(dateDeparture).isAfter(LocalDate.parse(dateArrival));
    }
}
