package ait.cohort49.hostel_casa_flamingo.service.interfaces;

import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;

import java.util.List;


public interface BookingService {

    List<BookingDto> getAllBookings(User user);

    BookingDto getBookingById(Long id, User user);

    BookingDto createBooking(BookingDto bookingDto, User user);

    BookingDto updateBooking(Long id, BookingDto bookingDto, User user);

    boolean deleteBooking(Long id, User user);
}
