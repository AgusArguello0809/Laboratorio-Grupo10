package FitStore.TpoGrupo10.presentation.dto.create;

import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

public class ProductoCreateDto {

    @NotNull(message = "El titulo del producto no puede ser nulo")
    @NotBlank(message = "El titulo del producto no puede ser vacio")
    @Size(min = 8, max = 100, message = "El titulo debe tener entre 8 y 100 caracteres")
    private String title;

    @NotNull(message = "La descripcion del producto no puede ser nula")
    @NotBlank(message = "La descripcion del producto no puede ser vacia")
    private String description;

    @Range(min = 1, max = 9999, message = "El stock debe estar entre 1 y 9999")
    private int stock;

    @DecimalMin(value = "1.0", message = "El precio del producto debe ser minimo $1.0")
    private double price;

    @NotNull(message = "El ID de la categoria del producto no puede ser nula")
    private Long categoryId;

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

