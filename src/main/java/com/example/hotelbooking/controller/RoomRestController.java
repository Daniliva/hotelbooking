package com.example.hotelbooking.controller;
import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.service.RoomService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomRestController {
    private final RoomService service;
    public RoomRestController(RoomService service) { this.service = service; }

    @GetMapping("/by-hotel/{hotelId}")
    public List<Room> getByHotel(@PathVariable Long hotelId) {
        return service.findByHotelId(hotelId);
    }

    @PostMapping
    public Room create(@RequestBody Room room) { return service.save(room); }
}
