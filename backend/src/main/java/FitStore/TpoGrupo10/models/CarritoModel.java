package FitStore.TpoGrupo10.models;

import java.util.List;

public class CarritoModel {
    private Long id;
    private UsuarioModel owner;
    private List<ItemCarritoModel> productos;
    private double total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioModel getOwner() {
        return owner;
    }

    public void setOwner(UsuarioModel owner) {
        this.owner = owner;
    }

    public List<ItemCarritoModel> getProductos() {
        return productos;
    }

    public void setProductos(List<ItemCarritoModel> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}