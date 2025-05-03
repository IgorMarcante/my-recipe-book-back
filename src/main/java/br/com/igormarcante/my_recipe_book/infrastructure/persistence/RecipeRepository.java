package br.com.igormarcante.my_recipe_book.infrastructure.persistence;

import br.com.igormarcante.my_recipe_book.domain.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Recipe save(Recipe recipe);
    List<Recipe> findAll();
    void deleteById(Long id);
    Optional<Recipe> findById(Long id);
}