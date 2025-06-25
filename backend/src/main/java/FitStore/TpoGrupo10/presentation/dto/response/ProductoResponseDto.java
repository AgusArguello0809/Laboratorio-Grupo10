package FitStore.TpoGrupo10.presentation.dto.response;

import java.util.List;

public class ProductoResponseDto {

    private Long id;
    private String title;
    private String description;
    private int stock;
    private double price;
    private Long categoryId;
    private String categoryName;
    private Long ownerId;
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

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getCategoryName() {
        return categoryName;
    }
}