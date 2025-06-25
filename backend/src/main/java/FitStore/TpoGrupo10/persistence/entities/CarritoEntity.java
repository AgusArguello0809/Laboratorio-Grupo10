package FitStore.TpoGrupo10.persistence.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class CarritoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UsuarioEntity owner;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<ItemCarritoEmbeddable> productos;

    private double total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioEntity getOwner() {
        return owner;
    }

    public void setOwner(UsuarioEntity owner) {
        this.owner = owner;
    }

    public List<ItemCarritoEmbeddable> getProductos() {
        return productos;
    }

    public void setProductos(List<ItemCarritoEmbeddable> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "CarritoEntity{" +
                "id=" + id +
                ", ownerId=" + (owner != null ? owner.getId() : null) +
                ", productos=" + (productos != null ? productos.size() : 0) + " items" +
                ", total=" + total +
                '}';
    }
}