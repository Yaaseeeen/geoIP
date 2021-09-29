package com.example.demo.service;

import com.example.demo.dto.RoomDto;
import com.example.demo.entity.Country;
import com.example.demo.entity.Room;
import com.example.demo.exception.RoomNotFoundException;
import com.example.demo.repository.CountryRepository;
import com.example.demo.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final CountryRepository countryRepository;

    public Set<Country> getCountries() {
        return new HashSet<>(countryRepository.findAll());
    }

    public List<RoomDto> getRooms() {
        return roomRepository.findAll().stream()
                .map(this::buildRoomDto)
                .collect(Collectors.toList());
    }

    public void addCountry(String countryName) {
        Country country = new Country();
        country.setName(countryName);
        countryRepository.save(country);
    }

    public RoomDto getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .map(this::buildRoomDto).orElseThrow(() -> new RoomNotFoundException("Room not found"));
    }

    public RoomDto create(RoomDto dto) {
        Room room = new Room();
        room.setName(dto.getName());
        room.setCountry(dto.getCountry());
        room = roomRepository.save(room);
        return buildRoomDto(room);
    }

    public RoomDto turnOn(Long roomId, RoomDto dto) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotFoundException("Room not found"));
        room.setTurnOn(dto.getTurnOn());
        room = roomRepository.save(room);
        return buildRoomDto(room);
    }

    private RoomDto buildRoomDto(Room room) {
        return new RoomDto()
                .setId(room.getId())
                .setName(room.getName())
                .setTurnOn(room.getTurnOn())
                .setCountry(room.getCountry());
    }
}
