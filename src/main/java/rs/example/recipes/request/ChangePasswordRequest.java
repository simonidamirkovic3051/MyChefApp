package rs.example.recipes.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private Long id;
    private String password;
}
