package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Hotel;
import com.example.hotelbooking.repository.HotelRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    private final HotelRepository repo;
    public HotelService(HotelRepository repo) { this.repo = repo; }

    public List<Hotel> findAll() { return repo.findAll(); }
    public Optional<Hotel> findById(Long id) { return repo.findById(id); }
    public Hotel save(Hotel hotel) { return repo.save(hotel); }
}