package me.mexx.recipeapp.model;

import lombok.*;

@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    private String name;
    private int count;
    private String measureUnit;

    @Override
    public String toString() {
        return name + " - " + count + " " + measureUnit;
    }
}
