package br.com.igormarcante.my_recipe_book.presentation.controller;

import br.com.igormarcante.my_recipe_book.appplication.dto.RecipeDTO;
import br.com.igormarcante.my_recipe_book.appplication.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeDTO> createRecipe(@Valid @RequestBody RecipeDTO recipeDTO) {
        RecipeDTO savedRecipe = recipeService.saveRecipe(recipeDTO);
        return ResponseEntity.ok(savedRecipe);
    }
}