package FitStore.TpoGrupo10.persistence.entities;

import jakarta.persistence.*;

import java.util.List;

// TODO: Tests unitarios
@Entity
@Table(name = "productos")
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoriaEntity category;

    private int stock;

    private double price;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private UsuarioEntity owner;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "producto_images", joinColumns = @JoinColumn(name = "producto_id"))
    @Column(name = "image_url")
    private List<String> images;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public CategoriaEntity getCategory() { return category; }
    public void setCategory(CategoriaEntity category) { this.category = category; }

    public UsuarioEntity getOwner() { return owner; }
    public void setOwner(UsuarioEntity owner) { this.owner = owner; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    @Override
    public String toString() {
        return "ProductoEntity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", category=" + (category != null ? category.getNombre() : null) +
                ", ownerId=" + (owner != null ? owner.getId() : null) +
                ", images=" + (images != null ? images.size() : 0) + " images" +
                '}';
    }
}
