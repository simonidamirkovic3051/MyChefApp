package rs.example.recipes.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RegisterUserRequest {

    @NotNull
    @Email
    private String email;
    @NotNull
    private String username;
    @NotNull
    private String password;

}
