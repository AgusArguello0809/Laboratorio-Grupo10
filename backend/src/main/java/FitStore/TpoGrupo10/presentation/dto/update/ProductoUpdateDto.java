package FitStore.TpoGrupo10.presentation.dto.update;

import jakarta.validation.constraints.*;

import java.util.List;

public class ProductoUpdateDto {

    @NotNull(message = "El titulo del producto no puede ser nulo")
    @NotBlank(message = "El titulo del producto no puede estar vacio")
    @Size(min = 8, max = 100, message = "El titulo debe tener entre 8 y 100 caracteres")
    private String title;

    @NotNull(message = "La descripcion del producto no puede ser nula")
    @NotBlank(message = "La descripcion del producto no puede estar vacia")
    private String description;

    @NotNull(message = "El stock del producto no puede ser nulo")
    private int stock;

    @DecimalMin(value = "1.0", message = "El precio del producto debe ser minimo $1.0")
    private double price;

    @NotNull(message = "La categoria del producto no puede ser nula")
    private Long categoryId;

    private List<String> existingImageUrls;

    // No se incluye el ownerId a propósito (no va a cambiar el id del usuario que haya cambiado el producto).

    public List<String> getExistingImageUrls() {
        return existingImageUrls;
    }

    public void setExistingImageUrls(List<String> existingImageUrls) {
        this.existingImageUrls = existingImageUrls;
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
