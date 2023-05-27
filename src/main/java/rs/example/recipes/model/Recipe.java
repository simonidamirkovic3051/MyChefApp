package rs.example.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "name is required!")
    private String name;
    private String coverPicUrl;
    //@NotNull(message = "prepTime is required!")
    private Integer prepTime;
    //@NotNull(message = "calories is required!")
    private Integer calories;
    //@NotNull(message = "difficulty is required!")
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @ManyToOne()
    @JoinColumn(name = "created_by")
    private User user;

    @ManyToMany()
    @JoinTable(
            name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @OneToMany(mappedBy = "recipe")
    private List<Step> steps;

    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> recipeIngredients;

    @ManyToMany(mappedBy = "favorites")
    @JsonIgnore
    private List<User> users;

    @OneToMany(mappedBy = "recipe")
    private List<Comment> comments;



}
