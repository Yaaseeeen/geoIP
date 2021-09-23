package com.example.demo.dto;

import com.example.demo.entity.Country;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoomDto {

    private Long id;
    private String name;
    private Boolean turnOn;
    private Country country;
}
