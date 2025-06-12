package FitStore.TpoGrupo10.presentation.dto;

public class AddProductRequestDto {
    private Long productId;
    private int cant;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
}
