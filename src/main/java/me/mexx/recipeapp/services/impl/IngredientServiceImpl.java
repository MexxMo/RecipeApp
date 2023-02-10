package me.mexx.recipeapp.services.impl;

import me.mexx.recipeapp.exception.ValidationException;
import me.mexx.recipeapp.model.Ingredient;
import me.mexx.recipeapp.services.IngredientService;
import me.mexx.recipeapp.services.ValidationService;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static long idCounter = 1;
    private final Map<Long, Ingredient> ingredients = new HashMap<>();
    private final ValidationService validationService;

    public IngredientServiceImpl(ValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }

        return ingredients.put(idCounter++, ingredient);
    }

    @Override
    public Optional<Ingredient> getById(Long id) {

        return Optional.ofNullable(ingredients.get(id));
    }
}