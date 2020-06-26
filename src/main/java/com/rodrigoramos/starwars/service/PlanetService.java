package com.rodrigoramos.starwars.service;

import com.rodrigoramos.starwars.model.Planet;

public interface PlanetService extends GenericService<Planet, Long> {

    Planet findByName(String name);

}