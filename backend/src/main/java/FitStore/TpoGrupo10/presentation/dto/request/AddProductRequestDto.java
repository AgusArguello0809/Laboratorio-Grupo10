package FitStore.TpoGrupo10.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Setter
@Getter
public class AddProductRequestDto {

    @NotNull(message = "El ID del producto es obligatorio")
    private Long productId;

    @Range(min = 1, max = 9999, message = "La cantidad debe ser al menos 1 y maximo 9999")
    private int cant;

}
