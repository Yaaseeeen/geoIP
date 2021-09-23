package com.example.demo.service;

import com.example.demo.dto.RoomDto;
import com.example.demo.entity.Room;
import com.example.demo.exception.RoomNotFoundException;
import com.example.demo.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl
        implements RoomService {
    private final RoomRepository roomRepository;

    @Override
    public List<RoomDto> getRooms() {
        return roomRepository.findAll().stream()
                .map(this::buildRoomDto)
                .collect(Collectors.toList());
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
