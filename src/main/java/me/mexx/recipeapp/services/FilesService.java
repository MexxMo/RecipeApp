package me.mexx.recipeapp.services;

import java.io.File;
import java.nio.file.Path;

public interface FilesService {
    boolean saveRecipeToFile(String json);

    String readRecipeFromFile();


    boolean cleanRecipeDataFile();

    boolean saveIngredientToFile(String json);

    String readIngredientFromFile();

    boolean cleanIngredientDataFile();

    File getRecipeFile();

    File getIngredientFile();

    Path tempFile(String suffix);
}
