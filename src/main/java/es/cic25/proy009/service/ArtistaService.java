package es.cic25.proy009.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic25.proy009.model.Artista;
import es.cic25.proy009.repository.ArtistaRepository;


@Transactional
@Service
public class ArtistaService {
// esta es una clase passThrough hasta que no implementemos lógica de negocio

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistaService.class);

    @Autowired
    ArtistaRepository artistaRepository;

    // Create
    public Artista create(Artista artista) {
        LOGGER.info("Se está creando un artista");
        artista = artistaRepository.save(artista);
        return artista;
    }

    // Read (1) (por id)
    @Transactional(readOnly = true)
    public Optional<Artista> get(Long id) {
        LOGGER.info("Se está leyendo un artista, si es que lo hay");
        Optional<Artista> artista = artistaRepository.findById(id);
        return artista;
    }

    // Read (All)
    @Transactional(readOnly = true)
    public List<Artista> get() {
        LOGGER.info("Se están listando todos los artistas");
        List<Artista> listaArtistas = artistaRepository.findAll();
        return listaArtistas; // y, en caso de no haber artistas, la retorna vacía
    }

    // Update
    public Artista update(Artista artista) {
        LOGGER.info("Se está actualizando un artista");
        artista = artistaRepository.save(artista);
        return artista;
    }

    // Delete (por id)
    public void delete(Long id) {
        LOGGER.info("Se está borrando un artista");
        artistaRepository.deleteById(id);
    }

}
