package me.mexx.recipeapp.services.impl;

import me.mexx.recipeapp.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {
    @Value("${path.to.data.files}")
    private String dataFilePath;
    @Value("${name.of.recipe.data.file}")
    private String recipeDataFile;
    @Value("${name.of.ingredient.data.file}")
    private String ingredientDataFile;

    @Override
    public boolean saveRecipeToFile(String json) {
        try {
            cleanRecipeDataFile();
            Files.writeString(Path.of(dataFilePath, recipeDataFile), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readRecipeFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, recipeDataFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanRecipeDataFile() {
        try {
            Files.deleteIfExists(Path.of(dataFilePath, recipeDataFile));
            Files.createFile(Path.of(dataFilePath, recipeDataFile));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean saveIngredientToFile(String json) {
        try {
            cleanIngredientDataFile();
            Files.writeString(Path.of(dataFilePath, ingredientDataFile), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readIngredientFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, ingredientDataFile));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanIngredientDataFile() {
        try {
            Files.deleteIfExists(Path.of(dataFilePath, ingredientDataFile));
            Files.createFile(Path.of(dataFilePath, ingredientDataFile));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}
