package br.com.igormarcante.my_recipe_book.appplication.service.impl;

import br.com.igormarcante.my_recipe_book.appplication.dto.IngredientDTO;
import br.com.igormarcante.my_recipe_book.appplication.dto.PrepStepDTO;
import br.com.igormarcante.my_recipe_book.appplication.dto.RecipeDTO;
import br.com.igormarcante.my_recipe_book.appplication.service.RecipeService;
import br.com.igormarcante.my_recipe_book.domain.entity.Ingredient;
import br.com.igormarcante.my_recipe_book.domain.entity.PrepStep;
import br.com.igormarcante.my_recipe_book.domain.entity.Recipe;
import br.com.igormarcante.my_recipe_book.domain.repository.RecipeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    @Transactional
    public RecipeDTO saveRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = Recipe.builder()
                .name(recipeDTO.getName())
                .prepTime(recipeDTO.getPrepTime())
                .yield(recipeDTO.getYield())
                .category(recipeDTO.getCategory())
                .build();

        if (recipeDTO.getIngredients() != null) {
            recipe.setIngredients(recipeDTO.getIngredients().stream()
                    .map(dto -> Ingredient.builder()
                            .name(dto.getName())
                            .quantity(dto.getQuantity())
                            .unit(dto.getUnit())
                            .recipe(recipe)
                            .build())
                    .collect(Collectors.toList()));
        }

        if (recipeDTO.getPrepSteps() != null) {
            recipe.setPrepSteps(recipeDTO.getPrepSteps().stream()
                    .map(dto -> PrepStep.builder()
                            .stepOrder(dto.getStepOrder())
                            .description(dto.getDescription())
                            .recipe(recipe)
                            .build())
                    .collect(Collectors.toList()));
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        return RecipeDTO.builder()
                .id(savedRecipe.getId())
                .name(savedRecipe.getName())
                .prepTime(savedRecipe.getPrepTime())
                .yield(savedRecipe.getYield())
                .category(savedRecipe.getCategory())
                .ingredients(savedRecipe.getIngredients().stream()
                        .map(ing -> IngredientDTO.builder()
                                .id(ing.getId())
                                .name(ing.getName())
                                .quantity(ing.getQuantity())
                                .unit(ing.getUnit())
                                .build())
                        .collect(Collectors.toList()))
                .prepSteps(savedRecipe.getPrepSteps().stream()
                        .map(step -> PrepStepDTO.builder()
                                .id(step.getId())
                                .stepOrder(step.getStepOrder())
                                .description(step.getDescription())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}