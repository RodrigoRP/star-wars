package com.rodrigoramos.starwars.controller.impl;

import com.rodrigoramos.starwars.controller.PlanetController;
import com.rodrigoramos.starwars.dto.PlanetDTO;
import com.rodrigoramos.starwars.mapper.PlanetMapper;
import com.rodrigoramos.starwars.model.Planet;
import com.rodrigoramos.starwars.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping("/api/planet")
@RestController
@RequiredArgsConstructor
public class PlanetControllerImpl implements PlanetController {

    private final PlanetService planetService;
    private final PlanetMapper planetMapper;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> save(@RequestBody PlanetDTO planetDTO) {
        Planet planet = planetMapper.toModel(planetDTO);
        planet = planetService.save(planet);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(planet.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Planet> findById(@PathVariable("id") Long id) {
        Planet planet = planetService.findById(id);
        return ResponseEntity.ok().body(planet);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        planetService.deleteById(id);
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Planet>> list() {
        List<Planet> planets = planetService.findAll();
        return ResponseEntity.ok().body(planets);
    }

    @Override
    @GetMapping("/name/{planetName}")
    public ResponseEntity<Planet> findByName(@PathVariable("planetName") String planetName) {
        Planet planet = planetService.findByName(planetName);
        return ResponseEntity.ok().body(planet);
    }
}