package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Booking;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.BookingRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BookingService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.BookingMappingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMappingService bookingMappingService;

    public BookingServiceImpl(BookingRepository bookingRepository, BookingMappingService bookingMappingService) {
        this.bookingRepository = bookingRepository;
        this.bookingMappingService = bookingMappingService;
    }

    @Override
    public List<BookingDto> getAllBookings(User user) {
        return user.getBookings()
                .stream()
                .map(bookingMappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public BookingDto getBookingById(Long id, User user) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent() && booking.get().getUser().equals(user)) {
            return bookingMappingService.mapEntityToDto(booking.get());
        }
        return null;
    }

    @Override
    public BookingDto createBooking(BookingDto bookingDto, User user) {
        Booking booking = bookingMappingService.mapDtoToEntity(bookingDto);
        booking.setUser(user);
        booking = bookingRepository.save(booking);
        user.getBookings().add(booking);
        return bookingMappingService.mapEntityToDto(booking);
    }

    @Override
    public BookingDto updateBooking(Long id, BookingDto bookingDto, User user) {
        Optional<Booking> existingBooking = bookingRepository.findById(id);
        if (existingBooking.isPresent() && existingBooking.get().getUser().equals(user)) {
            Booking booking = existingBooking.get();
            booking.setEntry(bookingDto.getEntry());
            booking.setDeparture(bookingDto.getDeparture());
            booking.setBookingStatus(bookingDto.getBookingStatus());
            booking.setBookingDate(bookingDto.getBookingDate());
            booking = bookingRepository.save(booking);
            return bookingMappingService.mapEntityToDto(booking);
        }
        return null;
    }

    @Override
    public boolean deleteBooking(Long id, User user) {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isPresent() && booking.get().getUser().equals(user)) {
            bookingRepository.delete(booking.get());
            return true;
        }
        return false;
    }
}
