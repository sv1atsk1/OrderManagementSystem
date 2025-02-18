package application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    private String name;

    @NotBlank(message = "Product description cannot be blank")
    private String description;

    @NotNull(message = "Product price cannot be null")
    @Positive(message = "Product price must be positive")
    private BigDecimal price;

    @NotNull(message = "Category ID cannot be null")
    @Positive(message = "Category ID must be positive")
    private Long categoryId;
}
