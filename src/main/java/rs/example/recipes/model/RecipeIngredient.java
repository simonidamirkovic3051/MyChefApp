package rs.example.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class RecipeIngredient {

    @EmbeddedId
    RecipeIngredientKey id = new RecipeIngredientKey();

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    @JsonIgnore
    Recipe recipe;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    Ingredient ingredient;

    private BigDecimal amount;

}
