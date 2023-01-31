package me.mexx.recipeapp.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class FirstController {

    @GetMapping("/")
    public String hello() {
        return "<h1>Приложение запущено</h1>" +
                "<h3>Привет!</h3>";
    }

    @GetMapping("/info")

    public String info() {
        return """
                <!DOCTYPE HTML>
                <html>
                 <head>
                  <meta charset="utf-8">
                  <title>Информация</title>
                 </head>
                 <body>
                  <h1 style="color:blue">Информация</h1>
                <ul>
                <li>Имя ученика: Губайдуллин Ильдар</li>\s
                <li>Название проекта: RecipeApp</li>\s
                <li>Дата создания проекта: 31.01.2023</li>\s
                <li>Описание проекта: Описание</li>\s
                </ul>
                 </body>
                </html>
                """;
    } // правильно или нет, не знаю, но работает)

}
