package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.exception.RestException;
import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.*;
import ait.cohort49.hostel_casa_flamingo.repository.BookingRepository;
import ait.cohort49.hostel_casa_flamingo.repository.CartRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BookingService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.BookingMappingService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CartRepository cartRepository;
    private final CartService cartService;
    private final BookingMappingService bookingMappingService;


    public BookingServiceImpl(BookingRepository bookingRepository,
                              CartRepository cartRepository,
                              CartService cartService,
                              BookingMappingService bookingMappingService) {
        this.bookingRepository = bookingRepository;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.bookingMappingService = bookingMappingService;
    }

    @Override
    public List<BookingDto> createBookingFromCart(User authUser) {

        Cart cart = cartRepository.findByUser(authUser).orElseThrow(() -> new RestException("Cart not found for user"));

        if (cart.getCartItemBeds().isEmpty()) {
            throw new RestException("Cart is empty, cannot create booking");
        }

        String bookingNumber = UUID.randomUUID().toString();

        List<Booking> bookingList = new ArrayList<>();

        List<CartItemBed> cartItemBeds = cart.getCartItemBeds();

        for (CartItemBed cartItemBed : cartItemBeds) {

            Booking booking = new Booking();
            booking.setUser(authUser);

            LocalDate entryDate = cartItemBed.getEntryDate();
            LocalDate departureDate = cartItemBed.getDepartureDate();

            booking.setEntryDate(entryDate);
            booking.setDepartureDate(departureDate);

            BigDecimal totalPrice = cartService.getTotalPrice(authUser);
            booking.setTotalPrice(totalPrice);

            booking.setBookingNumber(bookingNumber);

            Bed bed = cartItemBed.getBed();
            booking.setBed(bed);

            bookingList.add(booking);
        }

        bookingRepository.saveAll(bookingList);
        cartRepository.delete(cart);
        return bookingList
                .stream()
                .map(bookingMappingService::mapEntityToDto)
                .toList();
    }

    @Override
    public List<BookingDto> getBooking(User authUser) {
        return bookingRepository.findAllByUser(authUser)
                .stream()
                .map(bookingMappingService::mapEntityToDto)
                .toList();
    }
}
