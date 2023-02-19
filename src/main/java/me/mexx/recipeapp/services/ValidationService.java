package me.mexx.recipeapp.services;

import me.mexx.recipeapp.model.Ingredient;
import me.mexx.recipeapp.model.Recipe;

public interface ValidationService {
    boolean validate(Recipe recipe);

    boolean validate(Ingredient ingredient);

}
