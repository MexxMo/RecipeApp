package me.mexx.recipeapp.controllers;

import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RestController;

@RestController

public class FirstController {

    @GetMapping("/")
    public String hello() {
        return "Приложение запущено";
    }

    @GetMapping("/info")

    public String info() {
        return """
                <ul>
                <li>Имя ученика: Губайдуллин Ильдар</li>\s
                <li>Название проекта: RecipeApp</li>\s
                <li>Дата создания проекта: 31.01.2023</li>\s
                <li>Описание проекта: описание</li>\s
                </ul>
                """;
    }

}
