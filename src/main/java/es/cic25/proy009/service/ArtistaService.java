package es.cic25.proy009.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic25.proy009.model.Artista;
import es.cic25.proy009.model.Obra;
import es.cic25.proy009.repository.ArtistaRepository;
import es.cic25.proy009.repository.ObraRepository;



@Transactional
@Service
public class ArtistaService {
// esta es una clase passThrough hasta que no implementemos lógica de negocio

    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistaService.class);

    @Autowired
    ArtistaRepository artistaRepository;

    @Autowired
    ObraRepository obraRepository;

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

    // Delete (por id)
    public void delete(Long id) {
        LOGGER.info("Se está borrando un artista");
        artistaRepository.deleteById(id);
    }

    // Update
    public Artista update(Artista artistaConModificaciones) {
        LOGGER.info("Se está actualizando un artista");
        // ya hemos hecho las verificaciones pertinentes en Controller
        Long idArtistaConModificaciones = artistaConModificaciones.getId();
        Optional<Artista> artistaPorModificarOptional = artistaRepository.findById(idArtistaConModificaciones);
        if (artistaPorModificarOptional.isEmpty()) {
            throw new RuntimeException("No hay ningún artista con ese id"); // pese a haber hecho esta verificación, se ve
                                                                                    // que tengo que repetirla
        }
        Artista artistaPorModificar = artistaPorModificarOptional.get();
        // actualizamos individualmente cada campo, evitando invocar al save para hacer una actualización más selectiva en el
        // campo Obras
        artistaPorModificar.setActivo(artistaConModificaciones.isActivo());
        artistaPorModificar.setAniosExperiencia(artistaConModificaciones.getAniosExperiencia());
        artistaPorModificar.setEscuela(artistaConModificaciones.getEscuela());
        artistaPorModificar.setNombre(artistaPorModificar.getNombre());
        
        List<Obra> nuevaListaObras = artistaConModificaciones.getObras();
        List<Obra> originalListaObras = artistaPorModificar.getObras();

        Map<Long, Obra> mapaOriginal = originalListaObras.stream() // creo un stream para administrar todos los elementos
                                        .collect(Collectors.toMap(Obra::getId, obra -> obra)); // key: la id de la obra;
                                                                                               // value: la obra
        List<Obra> finalListaObras = new ArrayList<>();

        for (Obra obra : nuevaListaObras) {
            // si la id es nula, no está en base de datos, luego hay que crearla
            if (obra.getId() == null) {
                obra.setArtista(artistaPorModificar); // sabemos que en la lista original todas las Obras tienen todos sus campos,
                                                      // pero puede que en la nueva no. Para evitar problema, le asignamos el 
                                                      // artista
                finalListaObras.add(obra); // y añadimos a la lista
            }
            else if (mapaOriginal.containsKey(obra.getId())) { // es decir, si la key (el id) de algún elemento de la lista original
                                                               // de obras coincide con el nuevo
                Long idObra = obra.getId();
                Obra obraEnMapa = mapaOriginal.get(idObra);
                obraEnMapa.setTitulo(obra.getTitulo());
                obraEnMapa.setEstiloPictorico(obra.getEstiloPictorico());
                obraEnMapa.setFecha(obra.getFecha());
                obraEnMapa.setPrecio(obra.getPrecio());
                finalListaObras.add(obraEnMapa);
                mapaOriginal.remove(idObra); // quitamos del mapa original la obra; la tenemos en nuestra nueva lista
            }
            else { // si, por ejemplo, el id no es null pero tampoco está en base de datos
                throw new RuntimeException("La id de la obra que se pasa no se corresponde con ninguna en base de datos; sin sentido");
            }
        }
        

        // Ahora, dado que las obras que queden en mi mapa original no aparecen en la nueva lista recibiendo actualización,
        // eso significa que no existen ya
        for (Obra obra : mapaOriginal.values()) { // es decir, para los values (obras) en mi mapa
            obraRepository.delete(obra);
        }

        artistaPorModificar.setObras(finalListaObras); // ya aseguramos que esta será nuestra lista actualizada

        Artista artista = artistaRepository.save(artistaPorModificar);
        return artista;
    }
}