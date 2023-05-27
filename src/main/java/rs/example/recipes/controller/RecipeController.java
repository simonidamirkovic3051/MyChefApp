package rs.example.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import rs.example.recipes.model.Comment;
import rs.example.recipes.model.Difficulty;
import rs.example.recipes.model.Recipe;
import rs.example.recipes.model.User;
import rs.example.recipes.request.AddCommentRequest;
import rs.example.recipes.service.RecipeService;
import rs.example.recipes.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;

    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @GetMapping()
    public List<Recipe> findRecipes(@RequestParam(value = "name", required = false) String name,
                                    @RequestParam(value = "difficulty", required = false) Difficulty difficulty,
                                    @RequestParam(value = "prepTime", required = false) Integer prepTime,
                                    @RequestParam(value = "categories", required = false) String[] categories) {
        return recipeService.findByCriteria(name, difficulty, prepTime, categories);
    }

    @GetMapping("/created")
    public ResponseEntity<?> findCreatedByUser() {
        List<Recipe> recipes = recipeService.findAllForUser(getLoggedInUser());
        return ResponseEntity.ok(recipes);
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody Recipe recipe) {
        Recipe createdRecipe = recipeService.create(getLoggedInUser(), recipe);
        return ResponseEntity.ok(createdRecipe.getId());
    }

    @PutMapping()
    public ResponseEntity<?> update(@Valid @RequestBody Recipe recipe) {
        Recipe updatedRecipe = recipeService.update(recipe);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        recipeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-comment")
    public ResponseEntity<?> addComment(@Valid @RequestBody AddCommentRequest request) {
        Comment comment = recipeService.addComment(request, getLoggedInUser());
        return ResponseEntity.ok(comment);
    }

    private User getLoggedInUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        return userService.findOne(loggedInUser.getName());
    }




}
