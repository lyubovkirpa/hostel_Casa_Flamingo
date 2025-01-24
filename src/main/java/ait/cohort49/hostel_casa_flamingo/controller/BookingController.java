package ait.cohort49.hostel_casa_flamingo.controller;


import ait.cohort49.hostel_casa_flamingo.model.dto.BookingDto;
import ait.cohort49.hostel_casa_flamingo.model.entity.User;
import ait.cohort49.hostel_casa_flamingo.service.interfaces.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * Получить все бронирования
     */
    @GetMapping
    public List<BookingDto> getAllBookings(@AuthenticationPrincipal User user) {
        return bookingService.getAllBookings(user);
    }

    /**
     * Получить бронирование по ID
     */
    @GetMapping("/{id}")
    public BookingDto getBookingById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return bookingService.getBookingById(id, user);
    }

    /**
     * Создать новое бронирование
     */
    @PostMapping
    public BookingDto createBooking(@RequestBody BookingDto bookingDto, @AuthenticationPrincipal User user) {
        return bookingService.createBooking(bookingDto, user);
    }

    /**
     * Обновить существующее бронирование
     */
    @PutMapping("/{id}")
    public BookingDto updateBooking(@PathVariable Long id, @RequestBody BookingDto bookingDto, @AuthenticationPrincipal User user) {
        return bookingService.updateBooking(id, bookingDto, user);
    }

    /**
     * Удалить бронирование
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id, @AuthenticationPrincipal User user) {
        boolean isDeleted = bookingService.deleteBooking(id, user);
        if (isDeleted) {
            return ResponseEntity.ok("The reservation has been successfully deleted.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found.");
        }
    }

}
