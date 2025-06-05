package FitStore.TpoGrupo10.presentation.dto;

public class ProductoDto {
    private Long id;
    private String title;
    private String description;
    private int stock;
    private double price;

    private CategoriaDto category;
    private UsuarioDto owner;

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

    public CategoriaDto getCategory() { return category; }
    public void setCategory(CategoriaDto category) { this.category = category; }

    public UsuarioDto getOwner() { return owner; }
    public void setOwner(UsuarioDto owner) { this.owner = owner; }
}
