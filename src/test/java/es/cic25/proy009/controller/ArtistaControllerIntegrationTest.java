package es.cic25.proy009.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic25.proy009.model.Artista;
import es.cic25.proy009.repository.ArtistaRepository;
import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ArtistaControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private ArtistaRepository artistaRepository;

    @Test
    void testCreate() throws Exception {
        Artista artista = new Artista();
        artista.setNombre("Frida Kahlo");
        artista.setAniosExperiencia(12);
        artista.setEscuela("Autodidacta");
        artista.setActivo(true);

        String artistaJson = objectMapper.writeValueAsString(artista);

        mockMvc.perform(post("/artista")
                .contentType("application/json")
                .content(artistaJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String respuesta = result.getResponse().getContentAsString();
                    Artista registroCreado = objectMapper.readValue(respuesta, Artista.class);
                    assertTrue(registroCreado.getId() != null, "El valor no debe ser null");
                    Optional<Artista> registroRealmenteCreado = artistaRepository.findById(registroCreado.getId());
                    assertTrue(registroRealmenteCreado.isPresent());
                });
    }

    //@Test
    //void testCreateModificacion() throws Exception {
        //Artista artista = new Artista();
        //artista.setNombre("Frida Kahlo");
        //artista.setAniosExperiencia(12);
        //artista.setEscuela("Autodidacta");
        //artista.setActivo(true);
        //artista.setId(1L);

        //String artistaJson = objectMapper.writeValueAsString(artista);

        //mockMvc.perform(post("/artista")
        //      .contentType("application/json")
        //    .content(artistaJson))
        //       .andExpect(status().isBadRequest());
    //}

    @Test
    void testDelete() throws Exception {
        Artista artista = new Artista();
        artista.setNombre("Frida Kahlo");
        artista.setAniosExperiencia(12);
        artista.setEscuela("Autodidacta");
        artista.setActivo(true);

        String artistaJson = objectMapper.writeValueAsString(artista);

        MvcResult result = mockMvc.perform(post("/artista")
                .contentType("application/json")
                .content(artistaJson))
                .andExpect(status().isOk())
                .andReturn();

        String respuesta = result.getResponse().getContentAsString();
        Artista registroCreado = objectMapper.readValue(respuesta, Artista.class);
        Long idRegistroCreado = registroCreado.getId();

        mockMvc.perform(delete("/artista/" + idRegistroCreado))
                .andExpect(status().isOk());
    }

    @Test
    void testGetOne() throws Exception {
        Artista artista = new Artista();
        artista.setNombre("Frida Kahlo");
        artista.setAniosExperiencia(12);
        artista.setEscuela("Autodidacta");
        artista.setActivo(true);

        String artistaJson = objectMapper.writeValueAsString(artista);

        MvcResult result = mockMvc.perform(post("/artista")
                .contentType("application/json")
                .content(artistaJson))
                .andExpect(status().isOk())
                .andReturn();

        String respuesta = result.getResponse().getContentAsString();
        Artista registroCreado = objectMapper.readValue(respuesta, Artista.class);
        Long idRegistroCreado = registroCreado.getId();

        mockMvc.perform(get("/artista/" + idRegistroCreado))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAll() throws Exception {
        Artista artista1 = new Artista();
        artista1.setNombre("Frida Kahlo");
        artista1.setAniosExperiencia(12);
        artista1.setEscuela("Autodidacta");
        artista1.setActivo(true);

        Artista artista2 = new Artista();
        artista2.setNombre("Pablo Picasso");
        artista2.setAniosExperiencia(20);
        artista2.setEscuela("Real Academia");
        artista2.setActivo(false);

        String artista1Json = objectMapper.writeValueAsString(artista1);
        String artista2Json = objectMapper.writeValueAsString(artista2);

        MvcResult result1 = mockMvc.perform(post("/artista")
                .contentType("application/json")
                .content(artista1Json))
                .andExpect(status().isOk())
                .andReturn();

        MvcResult result2 = mockMvc.perform(post("/artista")
                .contentType("application/json")
                .content(artista2Json))
                .andExpect(status().isOk())
                .andReturn();

        String respuesta1 = result1.getResponse().getContentAsString();
        Artista registroCreado1 = objectMapper.readValue(respuesta1, Artista.class);

        String respuesta2 = result2.getResponse().getContentAsString();
        Artista registroCreado2 = objectMapper.readValue(respuesta2, Artista.class);

        MvcResult result = mockMvc.perform(get("/artista")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String respuesta = result.getResponse().getContentAsString();
        Artista[] arrayArtistas = objectMapper.readValue(respuesta, Artista[].class);

        assertEquals(arrayArtistas[0], registroCreado1);
        assertEquals(arrayArtistas[1], registroCreado2);
    }

    // faltan los de update
}

