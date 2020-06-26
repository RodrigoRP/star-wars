package com.rodrigoramos.starwars.service.impl;

import com.rodrigoramos.starwars.service.exception.*;
import com.rodrigoramos.starwars.dao.PlanetRepository;
import com.rodrigoramos.starwars.model.Planet;
import com.rodrigoramos.starwars.service.PlanetService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        try {
            repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não foi possível excluir!");
        }
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

}