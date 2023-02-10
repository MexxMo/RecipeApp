package me.mexx.recipeapp.services;

import me.mexx.recipeapp.model.Ingredient;

import java.util.Optional;

public interface IngredientService {
    Ingredient save(Ingredient ingredient);

    Optional<Ingredient> getById(Long id);
}
