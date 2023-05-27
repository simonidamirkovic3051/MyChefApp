package rs.example.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.example.recipes.model.Ingredient;
import rs.example.recipes.request.CreateIngredientRequest;
import rs.example.recipes.request.UpdateIngredientRequest;
import rs.example.recipes.service.IngredientService;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(ingredientService.findAll());
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody CreateIngredientRequest request) {
        Ingredient ingredient = ingredientService.create(request);
        return ResponseEntity.ok(ingredient);
    }

    @PutMapping
    public ResponseEntity<?> update(@Valid @RequestBody UpdateIngredientRequest request) {
        Ingredient ingredient = ingredientService.update(request);
        return ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        ingredientService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
