package rs.example.recipes.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RegisterResponse {
    private String message = "User created with username %s, for email %s";

    public RegisterResponse(String email, String username) {
        this.message = String.format(this.message, username, email);
    }
}
