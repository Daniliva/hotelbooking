package com.example.hotelbooking.service;

import com.example.hotelbooking.model.Room;
import com.example.hotelbooking.repository.RoomRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RoomService {
    private final RoomRepository repo;
    public RoomService(RoomRepository repo) { this.repo = repo; }

    public List<Room> findByHotelId(Long hotelId) { return repo.findByHotel_Id(hotelId); }
    public Room save(Room room) { return repo.save(room); }
}