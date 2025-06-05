package FitStore.TpoGrupo10.presentation.dto;

import java.util.List;

public class CarritoDto {

    private Long id;
    private UsuarioDto owner;
    private List<ItemCarritoDto> productos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioDto getOwner() {
        return owner;
    }

    public void setOwner(UsuarioDto owner) {
        this.owner = owner;
    }

    public List<ItemCarritoDto> getProductos() {
        return productos;
    }

    public void setProductos(List<ItemCarritoDto> productos) {
        this.productos = productos;
    }
}
