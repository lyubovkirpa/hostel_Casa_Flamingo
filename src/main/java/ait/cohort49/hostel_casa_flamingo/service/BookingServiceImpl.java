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

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final CartService cartService;
    private final BookingMappingService bookingMappingService;


    public BookingServiceImpl(BookingRepository bookingRepository,
                              CartService cartService,
                              BookingMappingService bookingMappingService) {
        this.bookingRepository = bookingRepository;
        this.cartService = cartService;
        this.bookingMappingService = bookingMappingService;
    }

    @Override
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
        cartService.delete(cart);
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
