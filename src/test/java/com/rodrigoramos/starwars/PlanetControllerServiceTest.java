package com.rodrigoramos.starwars;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rodrigoramos.starwars.dao.PlanetRepository;
import com.rodrigoramos.starwars.dto.PlanetDTO;
import com.rodrigoramos.starwars.model.Planet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PlanetControllerServiceTest {
    private final String BASE_URL = "/api/v1/planet";

    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private PlanetRepository mockRepository;

    @Before
    public void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    public void buscar_id_200() throws Exception {
        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");

        when(mockRepository.findById(1L)).thenReturn(Optional.of(planetOne));

        mockMvc.perform(get(BASE_URL + "/1"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("marte")))
                .andExpect(jsonPath("$.terrain", is("arido")))
                .andExpect(jsonPath("$.climate", is("temperado")));

        verify(mockRepository, times(1)).findById(1L);
    }

    @Test
    public void buscar_all_200() throws Exception {
        List<Planet> list = new ArrayList<>();
        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");
        final Planet planetTwo = new Planet(2L, "jupiter", "temperado", "arido");

        list.add(planetOne);
        list.add(planetTwo);
        when(mockRepository.findAll()).thenReturn(list);

        mockMvc.perform(get(BASE_URL))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("marte")))
                .andExpect(jsonPath("$[0].terrain", is("arido")))
                .andExpect(jsonPath("$[0].climate", is("temperado")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("jupiter")))
                .andExpect(jsonPath("$[1].terrain", is("arido")))
                .andExpect(jsonPath("$[1].climate", is("temperado")));

        verify(mockRepository, times(1)).findAll();
    }

    @Test
    public void buscar_nome_200() throws Exception {
        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");

        when(mockRepository.findByName(planetOne.getName())).thenReturn(Optional.of(planetOne));

        mockMvc.perform(get(BASE_URL + "/name/marte"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("marte")))
                .andExpect(jsonPath("$.terrain", is("arido")))
                .andExpect(jsonPath("$.climate", is("temperado")));

        verify(mockRepository, times(1)).findByName(planetOne.getName());
    }

    @Test
    public void buscar_id_404() throws Exception {
        mockMvc.perform(get(BASE_URL + "/2")).andExpect(status().isNotFound());
    }

    @Test
    public void criar_200() throws Exception {
        final PlanetDTO dto = new PlanetDTO("marte", "temperado", "arido");
        final Planet planetOne = new Planet(1L, dto.getName(), dto.getClimate(), dto.getGround());

        when(mockRepository.save(any(Planet.class))).thenReturn(planetOne);

        mockMvc.perform(post(BASE_URL)
                .content(objectMapper.writeValueAsString(dto))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(mockRepository, times(1)).save(any(Planet.class));
    }

    @Test
    public void deletar_200() throws Exception {

        final Planet planetOne = new Planet(1L, "marte", "temperado", "arido");

        when(mockRepository.findById(1L)).thenReturn(Optional.of(planetOne));

        mockMvc.perform(delete(BASE_URL + "/1"))
                .andExpect(status().isOk());

        verify(mockRepository, times(1)).deleteById(1L);
    }

    @Test
    public void buscar_filme_200() throws Exception {
        mockMvc.perform(get(BASE_URL + "/film/Tatooine"))
                .andExpect(content().contentType("application/json"))
                .andExpect(status().isOk());
    }


}