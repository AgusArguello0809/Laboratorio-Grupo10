package FitStore.TpoGrupo10.presentation.dto;

import jakarta.validation.constraints.*;

public class ProductoCreateDto {

    @NotBlank
    @Size(min = 8, max = 100)
    private String title;

    @NotBlank
    private String description;

    @Min(1)
    private int stock;

    @DecimalMin("1.0")
    private double price;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long ownerId;  // TODO: Este campo debe ser eliminado cuando se integre JWT. El ownerId será tomado desde el SecurityContext.

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

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}

