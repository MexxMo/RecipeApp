package me.mexx.recipeapp.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class FirstController {

    @GetMapping()
    public String hello() {
        return """
              
                  <title>Начальная страница</title>
                
                  <h1>Приложение запущено</h1>
                  <p><a href="/info">Обо мне</a></p>\s
               
                """;
    }

    @GetMapping("/info")

    public String info() {
        return """
                
                  <title>Информация</title>
                
                  <h1 style="color:blue">Информация</h1>
                <ul>
                <li>Имя ученика: Губайдуллин Ильдар</li>\s
                <li>Название проекта: RecipeApp</li>\s
                <li>Дата создания проекта: 31.01.2023</li>\s
                <li>Описание проекта: Описание</li>\s
                </ul>
                
                """;
    }

}
