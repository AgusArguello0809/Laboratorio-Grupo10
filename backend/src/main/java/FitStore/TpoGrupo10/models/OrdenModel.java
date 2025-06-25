package FitStore.TpoGrupo10.models;

import java.time.LocalDateTime;
import java.util.List;

public class OrdenModel {

    private Long id;
    private UsuarioModel comprador;
    private List<ItemOrdenModel> productos;
    private double total;
    private LocalDateTime fecha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioModel getComprador() {
        return comprador;
    }

    public void setComprador(UsuarioModel comprador) {
        this.comprador = comprador;
    }

    public List<ItemOrdenModel> getProductos() {
        return productos;
    }

    public void setProductos(List<ItemOrdenModel> productos) {
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
        return "OrdenModel{" +
                "id=" + id +
                ", comprador=" + (comprador != null ? comprador.getId() : null) +
                ", productos=" + (productos != null ? productos.size() + " items" : "null") +
                ", total=" + total +
                ", fecha=" + fecha +
                '}';
    }

    public static OrdenModel fromCarrito(CarritoModel carrito, List<ProductoModel> productosActuales) {
        OrdenModel orden = new OrdenModel();
        orden.setComprador(carrito.getOwner());
        orden.setFecha(LocalDateTime.now());
        orden.setTotal(carrito.getTotal());

        List<ItemOrdenModel> items = carrito.getProductos().stream().map(item -> {
            ProductoModel producto = productosActuales.stream()
                    .filter(p -> p.getId().equals(item.getProductoId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No se encontró el producto con ID " + item.getProductoId()));

            ItemOrdenModel nuevo = new ItemOrdenModel();
            nuevo.setProductoId(item.getProductoId());
            nuevo.setNombreProducto(producto.getTitle());
            nuevo.setCantidad(item.getCantidad());
            nuevo.setPrecioUnitario(item.getPrecioUnitario());
            nuevo.setSubTotal(item.getSubTotal());
            return nuevo;
        }).toList();

        orden.setProductos(items);
        return orden;
    }
}
