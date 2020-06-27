package com.rodrigoramos.starwars;

import com.rodrigoramos.starwars.dao.PlanetRepository;
import com.rodrigoramos.starwars.model.Planet;
import com.rodrigoramos.starwars.service.exception.ObjectNotFoundException;
import com.rodrigoramos.starwars.service.exception.PlanetRegistrationException;
import com.rodrigoramos.starwars.service.impl.PlanetServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class PlanetServiceImplTest {

    @InjectMocks
    PlanetServiceImpl planetService;

    @Mock
    PlanetRepository repository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllPlanetsTest() {
        List<Planet> list = new ArrayList<>();
        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");
        final Planet planetTwo = new Planet(2L, "jupiter", "temperado", "arido");

        list.add(planetOne);
        list.add(planetTwo);

        when(repository.findAll()).thenReturn(list);

        //test
        List<Planet> planetList = planetService.findAll();

        assertEquals(2, planetList.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void getPlanetByIdTest() {
        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");

        when(repository.findById(1L)).thenReturn(Optional.of(planetOne));

        Planet planet = planetService.findById(1L);

        assertEquals("marte", planet.getName());
        assertEquals("temperado", planet.getClimate());
        assertEquals("arido", planet.getTerrain());
    }

    @Test
    public void shouldThrowErrorWhenFindPlanetByIdTest() {
        final Long id = 1L;
        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");

        given(repository.findById(id)).willReturn(Optional.of(planetOne));

        assertThrows(ObjectNotFoundException.class, () -> planetService.findById(2L));

        verify(repository, never()).findById(22L);
    }


    @Test
    public void getPlanetByNameTest() {
        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");

        when(repository.findByName("marte")).thenReturn(Optional.of(planetOne));

        Planet planet = planetService.findByName("marte");

        assertEquals("marte", planet.getName());
        assertEquals("arido", planet.getTerrain());
    }

    @Test
    public void shouldThrowErrorWhenFindPlanetByNameTest() {
        final String planetName = "adamastor";
        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");

        given(repository.findByName("marte")).willReturn(Optional.of(planetOne));

        assertThrows(ObjectNotFoundException.class, () -> planetService.findByName(planetName));

        verify(repository, times(1)).findByName(planetName);
    }

    @Test
    public void createEmployeeTest() {
        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");

        planetService.save(planetOne);

        verify(repository, times(1)).save(planetOne);
    }

    @Test
    public void shouldBeDeletedPlanetByIdTest() {
        final Long planetId = 1L;
        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");

        given(repository.findById(planetId)).willReturn(Optional.of(planetOne));
        planetService.deleteById(planetId);

        verify(repository, times(1)).deleteById(planetId);
    }

    @Test
    public void shouldThrowErrorWhenPlanetAlreadyExistsTest() {
        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");
        final Planet planetTwo = new Planet(2L, "marte", "temperado", "arido");

        planetService.save(planetOne);

        given(repository.findByName("marte")).willReturn(Optional.of(planetOne));

        assertThrows(PlanetRegistrationException.class, () -> planetService.save(planetTwo));

        verify(repository, never()).save(planetTwo);
    }


    @Test
    public void testGetEmployeeListSuccess() {
        String namePlanet = "Tatooine";
        String namePlanet2 = "dasddqulas";

        int quantity = planetService.getQuantityPlanetShowInFilms(namePlanet);
        int quantity2 = planetService.getQuantityPlanetShowInFilms(namePlanet2);

        Assert.assertEquals(5, quantity);
        Assert.assertEquals(0, quantity2);
    }
}
