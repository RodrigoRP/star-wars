package com.rodrigoramos.starwars.mapper;

import com.rodrigoramos.starwars.dto.PlanetDTO;
import com.rodrigoramos.starwars.model.Planet;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlanetMapper {

    private final ModelMapper modelMapper;

    public Planet toModel(PlanetDTO planetDTO) {
        return modelMapper.map(planetDTO, Planet.class);
    }
}
