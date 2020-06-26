package com.rodrigoramos.starwars.bootstrap;

import com.rodrigoramos.starwars.dao.PlanetRepository;
import com.rodrigoramos.starwars.model.Planet;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Profile("test")
@RequiredArgsConstructor
@Component
public class BootStrapData implements CommandLineRunner {

    private final PlanetRepository planetRepository;

    @Override
    public void run(String... args) throws Exception {

        Planet planet1 = Planet.builder()
                .name("Tatooine")
                .climate("arid")
                .terrain("desert")
                .build();

        Planet planet2 = Planet.builder()
                .name("Alderaan")
                .climate("temperate")
                .terrain("mountains")
                .build();

        planetRepository.saveAll(Arrays.asList(planet1, planet2));
    }

}
