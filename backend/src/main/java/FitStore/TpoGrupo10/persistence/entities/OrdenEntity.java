package FitStore.TpoGrupo10.persistence.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class OrdenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UsuarioEntity comprador;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ItemOrdenEmbeddable> productos;

    private double total;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioEntity getComprador() {
        return comprador;
    }

    public void setComprador(UsuarioEntity comprador) {
        this.comprador = comprador;
    }

    public List<ItemOrdenEmbeddable> getProductos() {
        return productos;
    }

    public void setProductos(List<ItemOrdenEmbeddable> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "OrdenEntity{" +
                "id=" + id +
                ", compradorId=" + (comprador != null ? comprador.getId() : null) +
                ", productos=" + (productos != null ? productos.size() + " items" : "null") +
                ", total=" + total +
                ", fecha=" + fecha +
                '}';
    }
}
