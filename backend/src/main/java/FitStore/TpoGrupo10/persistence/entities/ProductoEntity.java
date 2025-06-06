package FitStore.TpoGrupo10.persistence.entities;

import FitStore.TpoGrupo10.models.ProductoModel;
import jakarta.persistence.*;

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

    public void updateFromModel(ProductoModel model, CategoriaEntity categoria, UsuarioEntity owner) {
        this.title = model.getTitle();
        this.description = model.getDescription();
        this.price = model.getPrice();
        this.stock = model.getStock();
        this.category = categoria;
        this.owner = owner;
    }
}
