package br.com.igormarcante.my_recipe_book.appplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private Integer prepTime;

    private String yield;

    private String category;

    private List<IngredientDTO> ingredients;

    private List<PrepStepDTO> prepSteps;
}