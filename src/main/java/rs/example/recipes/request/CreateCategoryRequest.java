package rs.example.recipes.request;

import lombok.Data;

@Data
public class CreateCategoryRequest {

    private String name;
    private String description;

}
