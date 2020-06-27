package com.rodrigoramos.starwars.service.impl;

import com.rodrigoramos.starwars.dao.PlanetRepository;
import com.rodrigoramos.starwars.model.PlanetsAPI;
import com.rodrigoramos.starwars.model.Planet;
import com.rodrigoramos.starwars.model.Results;
import com.rodrigoramos.starwars.service.PlanetService;
import com.rodrigoramos.starwars.service.exception.ObjectNotFoundException;
import com.rodrigoramos.starwars.service.exception.PlanetRegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
@Service
public class PlanetServiceImpl implements PlanetService {
    private final PlanetRepository repository;

    @Override
    public Planet save(Planet planet) {
        Optional<Planet> optionalPlanet = repository.findByName(planet.getName());
        if (optionalPlanet.isPresent()) {
            throw new PlanetRegistrationException("Planeta com o Nome: " + planet.getName() + " ja foi cadastrado.");
        }
        return repository.save(planet);
    }

    @Override
    public void deleteById(Long id) {
        findById(id);
        repository.deleteById(id);
    }

    @Override
    public Planet findById(Long id) {
        Optional<Planet> optionalPlanet = repository.findById(id);
        return optionalPlanet.orElseThrow(() -> new ObjectNotFoundException(
                "Planeta não encontrado! Id: " + id + ", Tipo: " + Planet.class.getName()));
    }

    @Override
    public List<Planet> findAll() {
        return repository.findAll();
    }

    @Override
    public Planet findByName(String name) {
        Optional<Planet> optionalPlanet = repository.findByName(name);
        return optionalPlanet.orElseThrow(() -> new ObjectNotFoundException(
                "Planeta não encontrado! Name: " + name + ", Tipo: " + Planet.class.getName()));
    }

    @Override
    public int getQuantityPlanetShowInFilms(String name) {
        RestTemplate template = new RestTemplate();
        int quantity = 0;

        UriComponents uri = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("swapi.dev/api")
                .path("planets/")
                .queryParam("search", name)
                .build();

        ResponseEntity<PlanetsAPI> entity = template
                .getForEntity(uri.toUriString(), PlanetsAPI.class);
        Results[] results = requireNonNull(entity.getBody()).getResults();
        if (results.length > 0)
            quantity = results[0].getFilms().length;

        return quantity;
    }
}