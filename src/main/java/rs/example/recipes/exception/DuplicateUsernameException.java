package rs.example.recipes.exception;

public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException(String username) {
        super(String.format("User with username %s already exists.", username));
    }
}
