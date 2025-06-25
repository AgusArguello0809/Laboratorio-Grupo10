package FitStore.TpoGrupo10.persistence.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class ItemCarritoEmbeddable {

    private Long productoId;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subTotal;

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "ItemCarritoEmbeddable{" +
                "productoId=" + productoId +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", subTotal=" + subTotal +
                '}';
    }
}
