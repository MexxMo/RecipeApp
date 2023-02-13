package me.mexx.recipeapp.services;

public interface FilesService {
    boolean saveRecipeToFile(String json);

    String readRecipeFromFile();


    boolean saveIngredientToFile(String json);

    String readIngredientFromFile();

}
