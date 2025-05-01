package br.com.igormarcante.my_recipe_book.appplication.service.impl;

import br.com.igormarcante.my_recipe_book.appplication.dto.IngredientDTO;
import br.com.igormarcante.my_recipe_book.appplication.dto.PrepStepDTO;
import br.com.igormarcante.my_recipe_book.appplication.dto.RecipeDTO;
import br.com.igormarcante.my_recipe_book.appplication.dto.RecipeSimpleDTO;
import br.com.igormarcante.my_recipe_book.appplication.service.RecipeService;
import br.com.igormarcante.my_recipe_book.domain.entity.Ingredient;
import br.com.igormarcante.my_recipe_book.domain.entity.PrepStep;
import br.com.igormarcante.my_recipe_book.domain.entity.Recipe;
import br.com.igormarcante.my_recipe_book.domain.repository.RecipeRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Override
    public List<RecipeSimpleDTO> getAllRecipes() {
        return recipeRepository.findAll().stream()
                .map(recipe -> RecipeSimpleDTO.builder()
                        .id(recipe.getId())
                        .name(recipe.getName())
                        .category(recipe.getCategory())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public RecipeDTO getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(recipe -> RecipeDTO.builder()
                        .id(recipe.getId())
                        .name(recipe.getName())
                        .prepTime(recipe.getPrepTime())
                        .yield(recipe.getYield())
                        .category(recipe.getCategory())
                        .ingredients(recipe.getIngredients().stream()
                                .map(ing -> IngredientDTO.builder()
                                        .id(ing.getId())
                                        .name(ing.getName())
                                        .quantity(ing.getQuantity())
                                        .unit(ing.getUnit())
                                        .build())
                                .collect(Collectors.toList()))
                        .prepSteps(recipe.getPrepSteps().stream()
                                .map(step -> PrepStepDTO.builder()
                                        .id(step.getId())
                                        .stepOrder(step.getStepOrder())
                                        .description(step.getDescription())
                                        .build())
                                .collect(Collectors.toList()))
                        .build())
                .orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    @Transactional
    public RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO) {
        // Buscar a receita
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        // Atualizar campos simples
        recipe.setName(recipeDTO.getName());
        recipe.setPrepTime(recipeDTO.getPrepTime());
        recipe.setYield(recipeDTO.getYield());
        recipe.setCategory(recipeDTO.getCategory());

        // Atualizar ingredientes
        updateIngredients(recipe, recipeDTO.getIngredients());

        // Atualizar passos de preparo
        updatePrepSteps(recipe, recipeDTO.getPrepSteps());

        // Salvar a receita
        Recipe updatedRecipe = recipeRepository.save(recipe);

        // Construir o DTO de resposta
        return RecipeDTO.builder()
                .id(updatedRecipe.getId())
                .name(updatedRecipe.getName())
                .prepTime(updatedRecipe.getPrepTime())
                .yield(updatedRecipe.getYield())
                .category(updatedRecipe.getCategory())
                .ingredients(updatedRecipe.getIngredients().stream()
                        .map(ing -> IngredientDTO.builder()
                                .id(ing.getId())
                                .name(ing.getName())
                                .quantity(ing.getQuantity())
                                .unit(ing.getUnit())
                                .build())
                        .collect(Collectors.toList()))
                .prepSteps(updatedRecipe.getPrepSteps().stream()
                        .map(step -> PrepStepDTO.builder()
                                .id(step.getId())
                                .stepOrder(step.getStepOrder())
                                .description(step.getDescription())
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    private void updateIngredients(Recipe recipe, List<IngredientDTO> ingredientDTOs) {
        // Mapear ingredientes existentes por ID
        Map<Long, Ingredient> existingIngredients = recipe.getIngredients().stream()
                .collect(Collectors.toMap(Ingredient::getId, ing -> ing));

        // Criar lista para novos ingredientes
        List<Ingredient> updatedIngredients = new ArrayList<>();

        // Processar cada IngredientDTO
        for (IngredientDTO dto : ingredientDTOs) {
            Ingredient ingredient;
            if (dto.getId() != null && existingIngredients.containsKey(dto.getId())) {
                // Atualizar ingrediente existente
                ingredient = existingIngredients.get(dto.getId());
                ingredient.setName(dto.getName());
                ingredient.setQuantity(dto.getQuantity());
                ingredient.setUnit(dto.getUnit());
                existingIngredients.remove(dto.getId()); // Remover da lista de existentes para não deletar
            } else {
                // Criar novo ingrediente
                ingredient = Ingredient.builder()
                        .name(dto.getName())
                        .quantity(dto.getQuantity())
                        .unit(dto.getUnit())
                        .recipe(recipe)
                        .build();
            }
            updatedIngredients.add(ingredient);
        }

        // Atualizar a coleção de ingredientes
        recipe.getIngredients().clear();
        recipe.getIngredients().addAll(updatedIngredients);
    }

    private void updatePrepSteps(Recipe recipe, List<PrepStepDTO> prepStepDTOs) {
        // Mapear passos de preparo existentes por ID
        Map<Long, PrepStep> existingPrepSteps = recipe.getPrepSteps().stream()
                .collect(Collectors.toMap(PrepStep::getId, step -> step));

        // Criar lista para novos passos
        List<PrepStep> updatedPrepSteps = new ArrayList<>();

        // Processar cada PrepStepDTO
        for (PrepStepDTO dto : prepStepDTOs) {
            PrepStep step;
            if (dto.getId() != null && existingPrepSteps.containsKey(dto.getId())) {
                // Atualizar passo existente
                step = existingPrepSteps.get(dto.getId());
                step.setStepOrder(dto.getStepOrder());
                step.setDescription(dto.getDescription());
                existingPrepSteps.remove(dto.getId()); // Remover da lista de existentes para não deletar
            } else {
                // Criar novo passo
                step = PrepStep.builder()
                        .stepOrder(dto.getStepOrder())
                        .description(dto.getDescription())
                        .recipe(recipe)
                        .build();
            }
            updatedPrepSteps.add(step);
        }

        // Atualizar a coleção de passos
        recipe.getPrepSteps().clear();
        recipe.getPrepSteps().addAll(updatedPrepSteps);
    }
}