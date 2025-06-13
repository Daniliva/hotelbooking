package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.service.BookingService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingRestController {
    private final BookingService service;

    public BookingRestController(BookingService service) {
        this.service = service;
    }

    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        booking.setUsername(username);
        return service.create(booking);
    }

    @GetMapping("/by-guest")
    public List<Booking> getByGuest() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return service.findByGuest(username);
    }
}