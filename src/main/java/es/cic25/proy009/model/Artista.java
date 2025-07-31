package es.cic25.proy009.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "anios_experiencia")
    private int aniosExperiencia;
    @Column(name = "escuela")
    private String escuela;
    @Column(name = "activo")
    private boolean activo;

    @OneToMany(mappedBy = "artista", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Obra> obras;
    
    public Artista() {

    }
    public Artista(Long id, String nombre, int aniosExperiencia, String escuela, boolean activo, List<Obra> obras) {
        this.id = id;
        this.nombre = nombre;
        this.aniosExperiencia = aniosExperiencia;
        this.escuela = escuela;
        this.activo = activo;
        this.obras = obras;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getAniosExperiencia() {
        return aniosExperiencia;
    }
    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }
    public String getEscuela() {
        return escuela;
    }
    public void setEscuela(String escuela) {
        this.escuela = escuela;
    }
    public boolean isActivo() {
        return activo;
    }
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    public List<Obra> getObras() {
        return obras;
    }
    public void setObras(List<Obra> obras) {
        this.obras = obras;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Artista other = (Artista) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Artista [id=" + id + ", nombre=" + nombre + ", aniosExperiencia=" + aniosExperiencia + ", escuela="
                + escuela + ", activo=" + activo + "]";
    }
    
}
