package me.mexx.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mexx.recipeapp.exception.ValidationException;
import me.mexx.recipeapp.model.Ingredient;
import me.mexx.recipeapp.services.FilesService;
import me.mexx.recipeapp.services.IngredientService;
import me.mexx.recipeapp.services.ValidationService;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static long idCounter = 1;
    private Map<Long, Ingredient> ingredients = new TreeMap<>();
    private final ValidationService validationService;
    private final FilesService filesService;

    public IngredientServiceImpl(ValidationService validationService, FilesService filesService) {
        this.validationService = validationService;
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        saveToFile();
        return ingredients.put(idCounter++, ingredient);
    }

    @Override
    public Optional<Ingredient> getById(Long id) {

        return Optional.ofNullable(ingredients.get(id));
    }

    @Override
    public Ingredient update(Long id, Ingredient ingredient) {
        if (!validationService.validate(ingredient)) {
            throw new ValidationException(ingredient.toString());
        }
        saveToFile();
        return ingredients.replace(id, ingredient);
    }

    @Override
    public Ingredient delete(Long id) {
        saveToFile();
        return ingredients.remove(id);
    }

    @Override
    public Map<Long, Ingredient> getAll() {
        return ingredients;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(ingredients);
            filesService.saveIngredientToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readIngredientFromFile();
            ingredients = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
