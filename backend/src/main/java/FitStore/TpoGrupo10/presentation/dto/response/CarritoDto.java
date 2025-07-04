package FitStore.TpoGrupo10.presentation.dto.response;

import java.util.List;

public class CarritoDto {

    private Long id;
    private List<ItemCarritoDto> productos;
    private double total;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemCarritoDto> getProductos() {
        return productos;
    }

    public void setProductos(List<ItemCarritoDto> productos) {
        this.productos = productos;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}