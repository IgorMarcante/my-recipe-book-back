package br.com.igormarcante.my_recipe_book.appplication.service;

import br.com.igormarcante.my_recipe_book.appplication.dto.RecipeDTO;

public interface RecipeService {
    RecipeDTO saveRecipe(RecipeDTO recipeDTO);
}