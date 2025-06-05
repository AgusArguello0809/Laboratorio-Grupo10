package FitStore.TpoGrupo10.persistence.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class ItemCarritoEmbeddable {
    private Long productoId;
    private int cantidad;


    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
