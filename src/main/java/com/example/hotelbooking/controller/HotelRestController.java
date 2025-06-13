package com.example.hotelbooking.controller;

import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelRestController {
    private final HotelService service;
    public HotelRestController(HotelService service) { this.service = service; }

    @GetMapping
    public List<Hotel> getAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public Hotel create(@RequestBody Hotel hotel) { return service.save(hotel); }
}