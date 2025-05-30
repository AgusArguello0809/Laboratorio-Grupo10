package FitStore.TPO_Grupo10.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name= "productos")
public class Producto {


    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY )
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
}

// --- Notas ---
// Jakarta Persistence (JPA):
// - @Entity: Mapea la clase a una tabla de la base de datos (en este caso, "productos").
// - @Id: Marca el campo 'id' como clave primaria.
// - @GeneratedValue: Hace que el valor de 'id' se genere automáticamente (autoincremental).
//
// Lombok:
// - @Data: Genera automáticamente getters, setters, toString, equals y hashCode para todos los campos.
// -------------
