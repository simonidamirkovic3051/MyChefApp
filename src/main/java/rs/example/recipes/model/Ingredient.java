package rs.example.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Enumerated(value = EnumType.STRING)
    private UnitOfMeasure uom;

    @OneToMany(mappedBy = "ingredient")
    @JsonIgnore
    private List<RecipeIngredient> recipeIngredients;

}
