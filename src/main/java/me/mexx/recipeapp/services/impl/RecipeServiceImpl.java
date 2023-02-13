package me.mexx.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.mexx.recipeapp.exception.ValidationException;
import me.mexx.recipeapp.model.Recipe;
import me.mexx.recipeapp.services.FilesService;
import me.mexx.recipeapp.services.RecipeService;
import me.mexx.recipeapp.services.ValidationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;


@Service
public class RecipeServiceImpl implements RecipeService {
    private final ValidationService validationService;
    private static long idCounter = 1;
    private final FilesService filesService;
    private Map<Long, Recipe> recipes = new TreeMap<>();

    public RecipeServiceImpl(ValidationService validationService, FilesService filesService) {
        this.validationService = validationService;
        this.filesService = filesService;
    }

    @PostConstruct
    private void init() {
        readFromFile();
    }

    @Override
    public Recipe save(Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        recipes.put(idCounter++, recipe);
        saveToFile();
        return recipe;
    }

    @Override
    public Optional<Recipe> getById(Long id) {
        return Optional.ofNullable(recipes.get(id));
    }

    @Override
    public Recipe update(Long id, Recipe recipe) {
        if (!validationService.validate(recipe)) {
            throw new ValidationException(recipe.toString());
        }
        saveToFile();
        return recipes.replace(id, recipe);
    }

    @Override
    public Recipe delete(Long id) {
        saveToFile();
        return recipes.remove(id);
    }

    @Override
    public Map<Long, Recipe> getAll() {
        return recipes;
    }

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipes);
            filesService.saveRecipeToFile(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            String json = filesService.readRecipeFromFile();
            recipes = new ObjectMapper().readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
