package es.cic25.proy009.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.cic25.proy009.model.Artista;
import es.cic25.proy009.service.ArtistaService;

@RestController
@RequestMapping("/artista")
public class ArtistaController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ArtistaController.class);
    @Autowired
    ArtistaService artistaService;

    // Create (exclusivamente por Body; no podríamos decirle que cree un Artista a partir del id)
    @PostMapping
    public Artista create(@RequestBody(required = true) Artista artista) { // el required, para que obligatoriamente nos pasen
                                                                           // un objeto en el body (el save no funciona con null)
        if (artista.getId() != null) {
            LOGGER.error("La petición para crear un artista tenía un id no nulo, luego no puede ser procesada");
            throw new IntentoCreacionSecurityException("Has tratado de preasignar el id a un artista");
        }
        else {
            LOGGER.info("Se está gestionando la petición para crear un artista");
            artista = artistaService.create(artista);
            return artista;
        }
    }

    // Read (1) (por id) (exclusivamente por URL; queremos obtener los datos, no pasarlos y volverlos a recibir)
    @GetMapping("/{id}")
    public Optional<Artista> get(@PathVariable(required = true) Long id) { // el required, para que obligatoriamente nos pasen
                                                                           // un id (si no, invocaríamos a get(), y además el
                                                                           // findById no funciona con null)
        LOGGER.info("Se está gestionando la petición para leer un artista por su id");
        Optional<Artista> artista = artistaService.get(id);
        return artista;
    }

    // Read (All)
    @GetMapping
    public List<Artista> get() {
        LOGGER.info("Se está gestionando la petición para listar todos los artistas");
        List<Artista> listaArtistas = artistaService.get();
        return listaArtistas;
    }

    // Update (por URL donde id tiene que coincidir con el del body de la petición)
    @PutMapping("/{id}")
    public Artista update(@PathVariable(required = true) Long id, @RequestBody(required = true) Artista artista) {
        if (id.equals(artista.getId())) { // si no hay incongruencias y las ids coinciden
            if (artistaService.get(id).isEmpty()) { // si no hay ningún artista en base de datos cuya id sea la que se pasa (el
                                                   // save no discrimina si esto sucede o no, simplemente guarda)
                LOGGER.error("Se ha intentado actualizar un artista sin estar en base de datos");
                throw new IntentoModificacionSecurityException("Has intentado modificar un artista que no está en base de datos");
            }
            else {
                LOGGER.info("Se está gestionando la petición para leer un artista");
                artista = artistaService.update(artista);
                return artista;
            }
        }
        else { // si las ids no coinciden
            LOGGER.error("Se ha intentado actualizar un artista dando ids contradictorias en cuerpo y URL");
            throw new IdsNoCoincidenException("La id del cuerpo de la petición y la de la URL no coinciden");
        }
    }

    // Delete (por id)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { // no es necesario el required, pues el Long que se le pasa al delete puede ser null
        LOGGER.info("Se está gestionando la petición para borrar un artista por su id");
        artistaService.delete(id);
    }
}
