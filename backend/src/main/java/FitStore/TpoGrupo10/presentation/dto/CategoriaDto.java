package FitStore.TpoGrupo10.presentation.dto;

import java.util.List;

public class CategoriaDto {

    private String id;
    private String nombre;
    private List<ProductoDto> productos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
