package ait.cohort49.hostel_casa_flamingo.service;

import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.Booking;
import ait.cohort49.hostel_casa_flamingo.model.entity.Cart;
import ait.cohort49.hostel_casa_flamingo.model.entity.CartItemBed;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.repository.BookingRepository;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BookingService;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.CartService;
import ait.cohort49.hostel_casa_flamingo.service.mapping.BookingMappingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CartService cartService;
    private final BookingMappingService bookingMappingService;
    private final BookingEmailService bookingEmailService;


    public BookingServiceImpl(BookingRepository bookingRepository,
                              CartService cartService,
                              BookingMappingService bookingMappingService,
                              BookingEmailService bookingEmailService) {
        this.bookingRepository = bookingRepository;
        this.cartService = cartService;
        this.bookingMappingService = bookingMappingService;
        this.bookingEmailService = bookingEmailService;
    }

    @Override
    @Transactional
    public List<BookingDto> createBookingFromCart(User authUser) {

        Cart cart = cartService.getCartEntity(authUser);

        List<CartItemBed> cartItemBeds = cart.getCartItemBeds();
        if (cartItemBeds == null || cartItemBeds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Booking> bookingList = new ArrayList<>();
        for (CartItemBed cartItemBed : cartItemBeds) {
            Booking booking = new Booking(
                    cartItemBed.getEntryDate(),
                    cartItemBed.getDepartureDate(),
                    cartService.getTotalPrice(authUser),
                    authUser,
                    cartItemBed.getBed()
            );
            bookingList.add(booking);
        }

        bookingRepository.saveAll(bookingList);
//        authUser.setCart(null);
//        cartService.delete(cart);

        List<BookingDto> mappedBookingDtos = bookingList
                .stream()
                .map(bookingMappingService::mapEntityToDto)
                .toList();
        bookingEmailService.sendBookingConfirmationEmail(mappedBookingDtos, authUser);
        return mappedBookingDtos;
    }

    @Override
    public List<BookingDto> getBooking(User authUser) {
        return bookingRepository.findAllByUser(authUser)
                .stream()
                .map(bookingMappingService::mapEntityToDto)
                .toList();
    }
}
