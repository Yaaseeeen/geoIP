package com.example.demo.controller;

import com.example.demo.dto.RoomDto;
import com.example.demo.service.RoomServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomServiceImpl roomServiceImpl;

    public RoomController(RoomServiceImpl roomServiceImpl) {
        this.roomServiceImpl = roomServiceImpl;
    }

    @GetMapping(produces = "application/json")
    public List<RoomDto> getRooms() {
        return roomServiceImpl.getRooms();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public RoomDto addRoom(@RequestBody RoomDto dto) {
        return roomServiceImpl.create(dto);
    }

    @GetMapping("/{id}")
    public RoomDto getRoom(@PathVariable Long id) {
        return roomServiceImpl.getRoom(id);
    }

    @PostMapping("/{id}")
    public RoomDto turnOn(@PathVariable Long id, @RequestBody RoomDto dto) {
        return roomServiceImpl.turnOn(id, dto);
    }

}
