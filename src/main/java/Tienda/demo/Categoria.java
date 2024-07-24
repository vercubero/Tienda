package Tienda.demo;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data // Automaticamente crear los set y get
@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long idChtegoria;
    private String descripcion;
    private String rutalmagen; // Hibernate lo transforma en ruta imagen 
    private boolean activo;

    public Categoria() {
    }

    public Categoria(String descripcion, String rutalmagen, boolean activo) {
        this.descripcion = descripcion;
        this.rutalmagen = rutalmagen;
        this.activo = activo;
    }
}
