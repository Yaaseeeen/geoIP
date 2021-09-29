package com.example.demo.controller;

import com.example.demo.dto.RoomDto;
import com.example.demo.entity.Country;
import com.example.demo.entity.GeoIP;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.service.RawDBDemoGeoIPLocationService;
import com.example.demo.service.RoomService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/rooms")
public class RoomController {

    private final RoomService roomService;
    private final RawDBDemoGeoIPLocationService locationService;

    public RoomController(RoomService roomService, RawDBDemoGeoIPLocationService locationService) {
        this.roomService = roomService;
        this.locationService = locationService;
    }

    @GetMapping("/")
    public List<RoomDto> getRoomsAndAddCountry() throws Exception {
        String country = getLocation().getCountry();
        if (!getCountries().toString().contains(country)) {
            roomService.addCountry(country);
        }
        return roomService.getRooms();
    }

    @GetMapping("/countries")
    public Set<Country> getCountries() {
        return roomService.getCountries();
    }

    @PostMapping("/")
    public RoomDto addRoom(@RequestBody RoomDto dto) {
        return roomService.create(dto);
    }

    @GetMapping("/{id}")
    public RoomDto getRoom(@PathVariable Long id) {
        return roomService.getRoom(id);
    }

    @PatchMapping("/{id}")
    public RoomDto turnOn(@PathVariable Long id, @RequestBody RoomDto dto) {
        return roomService.turnOn(id, dto);
    }

    @PatchMapping("check/{id}")
    private void canUpdate(@PathVariable Long id) throws Exception {
        RoomDto room = getRoom(id);
        if (!getLocation().getCountry().equalsIgnoreCase(room.getCountry().getName())) {
            throw new AccessDeniedException("У вас нет доступа к этой комнате");
        }
    }

    @RequestMapping(value = "/qq", method = RequestMethod.POST)
    @ResponseBody
    public GeoIP getLocation() throws Exception {
        HttpServletRequest requestq = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        String remoteAddress = getRemoteIP(RequestContextHolder.currentRequestAttributes());
        return locationService.getLocation("170.130.126.202");
    }

    private static final String[] IP_HEADER_NAMES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    public static String getRemoteIP(RequestAttributes requestAttributes) {
        if (requestAttributes == null) {
            return "0.0.0.0";
        }
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String ip = Arrays.stream(IP_HEADER_NAMES)
                .map(request::getHeader)
                .filter(h -> h != null && h.length() != 0 && !"unknown".equalsIgnoreCase(h))
                .map(h -> h.split(",")[0])
                .reduce("", (h1, h2) -> h1 + ":" + h2);
        return ip + request.getRemoteAddr();
    }
}
