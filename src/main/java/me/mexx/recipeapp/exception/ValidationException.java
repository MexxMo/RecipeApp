package me.mexx.recipeapp.exception;


public class ValidationException extends RuntimeException {
    public ValidationException(String entity) {
        super("Ошибка валидации " + entity);
    }
}
