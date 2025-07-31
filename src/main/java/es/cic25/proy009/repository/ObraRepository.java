package es.cic25.proy009.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.cic25.proy009.model.Obra;

public interface ObraRepository extends JpaRepository<Obra, Long>{

    // indicamos a JPA que queremos encontrar la lista de obras cuya artista_id asociada es una concreta
    public List<Obra> findByArtistaId(Long artistaId);
}
