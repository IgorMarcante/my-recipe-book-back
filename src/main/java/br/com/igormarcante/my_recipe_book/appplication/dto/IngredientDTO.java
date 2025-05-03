package br.com.igormarcante.my_recipe_book.appplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IngredientDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Quantity is mandatory")
    private String quantity;

    @NotBlank(message = "Unit is mandatory")
    private String unit;
}