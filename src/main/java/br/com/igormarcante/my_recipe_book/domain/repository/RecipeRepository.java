package br.com.igormarcante.my_recipe_book.domain.repository;

import br.com.igormarcante.my_recipe_book.domain.entity.Recipe;

public interface RecipeRepository {
    Recipe save(Recipe recipe);
}