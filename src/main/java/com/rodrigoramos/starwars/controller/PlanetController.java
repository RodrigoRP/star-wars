package com.rodrigoramos.starwars.controller;

import com.rodrigoramos.starwars.dto.PlanetDTO;
import com.rodrigoramos.starwars.model.Planet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(tags = "Planet")
public interface PlanetController {
    @ApiOperation("Add new planet")
    ResponseEntity<Void> save(@RequestBody PlanetDTO planetDTO);

    @ApiOperation("Find planet by Id")
    ResponseEntity<Planet> findById(@PathVariable("id") Long id);

    @ApiOperation("Delete planet based on primary key")
    void delete(@PathVariable("id") Long id);

    @ApiOperation("Find all planets")
    ResponseEntity<List<Planet>> list();

    @ApiOperation("Find planet by Name")
    ResponseEntity<Planet> findByName(@PathVariable("planetName") String name);

   @ApiOperation("Get the amount planet appears in the films")
   ResponseEntity<Integer> getAmountFilmAppears(@PathVariable("filmName") String filmName);
}