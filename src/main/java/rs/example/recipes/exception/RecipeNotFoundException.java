package rs.example.recipes.exception;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(Long id) {
        super(String.format("Recipe with id %d not found", id));
    }
}
