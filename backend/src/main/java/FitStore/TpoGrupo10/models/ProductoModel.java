package FitStore.TpoGrupo10.models;

import java.util.List;

public class ProductoModel {

    private Long id;
    private String title;
    private String description;
    private int stock;
    private double price;
    private List<String> images;

    private CategoriaModel category;
    private UsuarioModel owner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CategoriaModel getCategory() {
        return category;
    }

    public void setCategory(CategoriaModel category) {
        this.category = category;
    }

    public UsuarioModel getOwner() {
        return owner;
    }

    public void setOwner(UsuarioModel owner) {
        this.owner = owner;
    }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public void updateFrom(ProductoModel source) {
        this.title = source.getTitle();
        this.description = source.getDescription();
        this.stock = source.getStock();
        this.price = source.getPrice();
        this.category = source.getCategory();
        this.images = source.getImages();
    }
}
