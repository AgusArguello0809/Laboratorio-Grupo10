package FitStore.TpoGrupo10.models;

public class CategoriaModel {

    private Long id;
    private String nombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "CategoriaModel{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}