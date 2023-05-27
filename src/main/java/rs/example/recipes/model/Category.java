package rs.example.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToMany(mappedBy = "categories")
    @JsonIgnore
    private List<Recipe> recipes;

    public void deleteRecipe(Recipe recipe) {
        recipes.remove(recipe);
        recipe.getCategories().remove(this);
    }

}
