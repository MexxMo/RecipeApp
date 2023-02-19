package me.mexx.recipeapp.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String name;
    private int cookingTime;
    private List<Ingredient> ingredients;
    private List<String> steps;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append("\n");
        builder.append("Время приготовления: ").append(cookingTime).append(" минут\n");
        builder.append("Ингредиенты:\n");
        for (Ingredient ingredient : ingredients) {
            builder.append("◦ ").append(ingredient).append("\n");
        }
        builder.append("Инструкция по приготовлению:\n");
        for (int i = 0; i < steps.size(); i++) {
            builder.append(i + 1).append(" - ").append(steps.get(i)).append("\n");
        }
        return builder.toString();
    }
}

