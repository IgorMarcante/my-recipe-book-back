package br.com.igormarcante.my_recipe_book.appplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrepStepDTO {

    private Long id;

    @NotNull(message = "Step order is mandatory")
    private Integer stepOrder;

    @NotBlank(message = "Description is mandatory")
    private String description;
}