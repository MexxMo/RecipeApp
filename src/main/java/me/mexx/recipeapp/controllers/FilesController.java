package me.mexx.recipeapp.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.mexx.recipeapp.services.FilesService;
import me.mexx.recipeapp.services.RecipeService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;


@RestController
@RequestMapping("/files")
@Tag(name = "API для работы с файлами")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Всё хорошо, запрос выполнился."),
        @ApiResponse(responseCode = "400", description = "Есть ошибка в параметрах запроса."),
        @ApiResponse(responseCode = "404", description = "URL неверный или такого действия нет в веб-приложении."),
        @ApiResponse(responseCode = "500", description = "Во время выполнения запроса произошла ошибка на сервере.")
})
@RequiredArgsConstructor
public class FilesController {

    private final FilesService filesService;
    private final RecipeService recipeService;



    @GetMapping(value = "/export/recipe", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Экспорт рецептов в файл .json")
    public ResponseEntity<InputStreamResource> downloadRecipe() throws FileNotFoundException {
        File recipeFile = filesService.getRecipeFile();
        if (recipeFile.exists()) {
            InputStreamResource resource = new InputStreamResource(new FileInputStream(recipeFile));
            return ResponseEntity.ok()
                    .contentLength(recipeFile.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipe.json\"")
                    .body(resource);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/import/recipe", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт рецептов из файла .json")
    public ResponseEntity<Void> uploadRecipeFile(@RequestParam MultipartFile file) {
        filesService.cleanRecipeDataFile();
        File recipeFile = filesService.getRecipeFile();

        try (FileOutputStream fos = new FileOutputStream(recipeFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
    }

    @PostMapping(value = "/import/ingredient", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Импорт ингредиентов из файла .json")
    public ResponseEntity<Void> uploadIngredientFile(@RequestParam MultipartFile file) {
        filesService.cleanIngredientDataFile();
        File ingredientFile = filesService.getIngredientFile();
        try (FileOutputStream fos = new FileOutputStream(ingredientFile)) {
            IOUtils.copy(file.getInputStream(), fos);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @GetMapping(value = "export/recipe/txt")
    @Operation(summary = "Экспорт рецептов в файл .txt")
    public ResponseEntity<InputStreamResource> getRecipeTxtFile() {
        try {
            Path path = recipeService.recipeTxtFile();

            InputStreamResource resource = new InputStreamResource(new FileInputStream(path.toFile()));
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .contentLength(Files.size(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Recipes.txt\"")
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().build();

    }

}
