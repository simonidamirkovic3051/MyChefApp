package rs.example.recipes.exception;

public class IngredientNotFoundException extends RuntimeException {

    public IngredientNotFoundException(Long id) {
        super(String.format("Ingredient with id %d not found", id));
    }
}
