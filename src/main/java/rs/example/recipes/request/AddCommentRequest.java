package rs.example.recipes.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddCommentRequest {

    @NotNull
    private String text;
    @NotNull
    private Long recipeId;

}
