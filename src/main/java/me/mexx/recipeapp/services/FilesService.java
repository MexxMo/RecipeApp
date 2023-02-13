package me.mexx.recipeapp.services;

import java.io.File;

public interface FilesService {
    boolean saveRecipeToFile(String json);

    String readRecipeFromFile();


    boolean cleanRecipeDataFile();

    boolean saveIngredientToFile(String json);

    String readIngredientFromFile();

    boolean cleanIngredientDataFile();

    File getRecipeFile();

    File getIngredientFile();
}
