package rs.example.recipes.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class RecipeIngredientKey implements Serializable {

    @Column(name = "recipe_id")
    Long recipeId;

    @Column(name = "ingredient_id")
    Long ingredientId;

}
