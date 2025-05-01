package br.com.igormarcante.my_recipe_book.appplication.service;

import br.com.igormarcante.my_recipe_book.appplication.dto.RecipeDTO;
import br.com.igormarcante.my_recipe_book.appplication.dto.RecipeSimpleDTO;

import java.util.List;

public interface RecipeService {
    RecipeDTO saveRecipe(RecipeDTO recipeDTO);
    List<RecipeSimpleDTO> getAllRecipes();
    RecipeDTO getRecipeById(Long id);

    void deleteById(Long id);
    RecipeDTO updateRecipe(Long id, RecipeDTO recipeDTO);
}