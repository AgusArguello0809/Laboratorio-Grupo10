package FitStore.TpoGrupo10.presentation.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public class OrdenDto {

    private Long id;
    private Long compradorId;
    private List<ItemOrdenDto> productos;
    private double total;
    private LocalDateTime fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompradorId() {
        return compradorId;
    }

    public void setCompradorId(Long compradorId) {
        this.compradorId = compradorId;
    }

    public List<ItemOrdenDto> getProductos() {
        return productos;
    }

    public void setProductos(List<ItemOrdenDto> productos) {
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
}

