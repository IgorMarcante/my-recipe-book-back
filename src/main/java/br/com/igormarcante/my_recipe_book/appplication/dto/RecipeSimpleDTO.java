package br.com.igormarcante.my_recipe_book.appplication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeSimpleDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    private String category;
}