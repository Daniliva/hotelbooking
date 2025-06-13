package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.Booking;
import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.service.BookingService;
import com.example.hotelbooking.service.HotelService;
import com.example.hotelbooking.service.RoomService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class WebController {
    private final HotelService hotelService;
    private final RoomService roomService;
    private final BookingService bookingService;

    public WebController(HotelService hotelService, RoomService roomService, BookingService bookingService) {
        this.hotelService = hotelService;
        this.roomService = roomService;
        this.bookingService = bookingService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/ui/hotels")
    public String hotels(Model model) {
        List<Hotel> hotels = hotelService.findAll();
        model.addAttribute("hotels", hotels);
        return "hotels";
    }

    @GetMapping("/ui/rooms/by-hotel/{hotelId}")
    public String rooms(@PathVariable Long hotelId, Model model) {
        List<Room> rooms = roomService.findByHotelId(hotelId);
        String hotelName = hotelService.findById(hotelId)
                .map(Hotel::getName)
                .orElse("Unknown Hotel");
        model.addAttribute("rooms", rooms);
        model.addAttribute("hotelName", hotelName);
        return "rooms";
    }

    @GetMapping("/ui/bookings")
    public String bookings(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Booking> bookings = bookingService.findByGuest(username);
        model.addAttribute("bookings", bookings);
        return "bookings";
    }
}