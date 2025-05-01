package br.com.igormarcante.my_recipe_book.domain.repository;

import br.com.igormarcante.my_recipe_book.domain.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaRecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepository {
}