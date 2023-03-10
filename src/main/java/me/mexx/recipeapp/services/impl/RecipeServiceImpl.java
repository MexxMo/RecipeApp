package me.mexx.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.mexx.recipeapp.exception.ValidationException;
import me.mexx.recipeapp.model.Recipe;
import me.mexx.recipeapp.services.FilesService;
import me.mexx.recipeapp.services.RecipeService;
import me.mexx.recipeapp.services.ValidationService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;


@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {
    private final ValidationService validationService;
    private static long idCounter = 1;
    private final FilesService filesService;

    private Map<Long, Recipe> recipes = new TreeMap<>();


    @PostConstruct
    private void init() {
        try {
            readFromFile();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        Recipe recipe1 = recipes.replace(id, recipe);
        saveToFile();
        return recipe1;
    }

    @Override
    public Recipe delete(Long id) {
        Recipe remove = recipes.remove(id);
        saveToFile();
        return remove;
    }

    @Override
    public Map<Long, Recipe> getAll() {
        return recipes;
    }

    @Override
    public Path recipeTxtFile() throws IOException {
        Path path = filesService.tempFile("recipes");
        try (Writer writer = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            for (Recipe recipe : recipes.values()) {
                writer.append(recipe.toString());
                writer.append("\n");
            }
        }
        return path;
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
