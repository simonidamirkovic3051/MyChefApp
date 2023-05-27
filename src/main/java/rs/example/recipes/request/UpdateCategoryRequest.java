package rs.example.recipes.request;

import lombok.Data;

@Data
public class UpdateCategoryRequest {

    private Long id;
    private String name;
    private String description;

}
