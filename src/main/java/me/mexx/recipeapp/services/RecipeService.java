package me.mexx.recipeapp.services;


import me.mexx.recipeapp.model.Recipe;

import java.util.Map;
import java.util.Optional;

public interface RecipeService {
    Recipe save(Recipe Recipe);

    Optional<Recipe> getById(Long id);

    Recipe update(Long id, Recipe recipe);

    Recipe delete(Long id);

    Map<Long, Recipe> getAll();
}
