package me.mexx.recipeapp.services.impl;

import me.mexx.recipeapp.services.FilesService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FilesServiceImpl implements FilesService {
    @Value("${path.to.data.files}")
    private String dataFilePath;
    @Value("${name.of.recipe.data.file}")
    private String recipeDataFileName;
    @Value("${name.of.ingredient.data.file}")
    private String ingredientDataFileName;

    @Override
    public boolean saveRecipeToFile(String json) {
        try {
            cleanRecipeDataFile();
            Files.writeString(Path.of(dataFilePath, recipeDataFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readRecipeFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, recipeDataFileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanRecipeDataFile() {
        Path path = Path.of(dataFilePath, recipeDataFileName);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean saveIngredientToFile(String json) {
        try {
            cleanIngredientDataFile();
            Files.writeString(Path.of(dataFilePath, ingredientDataFileName), json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readIngredientFromFile() {
        try {
            return Files.readString(Path.of(dataFilePath, ingredientDataFileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean cleanIngredientDataFile() {
        Path path = Path.of(dataFilePath, ingredientDataFileName);
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public File getRecipeFile() {
        return new File(dataFilePath + "/" + recipeDataFileName);
    }

    @Override
    public File getIngredientFile() {
        return new File(dataFilePath + "/" + ingredientDataFileName);
    }

    @Override
    public Path tempFile(String random) {
        try {
            return Files.createTempFile(Path.of(dataFilePath), "tempFile", random);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
