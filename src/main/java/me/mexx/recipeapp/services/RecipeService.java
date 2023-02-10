package me.mexx.recipeapp.services;

import me.mexx.recipeapp.model.Recipe;

import java.util.Optional;

public interface RecipeService {
    Recipe save(Recipe Recipe);

    Optional<Recipe> getById(Long id);

}
