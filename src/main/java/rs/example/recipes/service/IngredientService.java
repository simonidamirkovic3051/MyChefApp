package rs.example.recipes.service;

import org.springframework.stereotype.Service;
import rs.example.recipes.exception.IngredientNotFoundException;
import rs.example.recipes.model.Ingredient;
import rs.example.recipes.repository.IngredientRepository;
import rs.example.recipes.repository.RecipeIngredientRepository;
import rs.example.recipes.request.CreateIngredientRequest;
import rs.example.recipes.request.UpdateIngredientRequest;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    public IngredientService(IngredientRepository ingredientRepository, RecipeIngredientRepository recipeIngredientRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    public Ingredient findOne(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException(id));
    }

    public List<Ingredient> findAll() {
        return ingredientRepository.findAll();
    }

    public Ingredient create(CreateIngredientRequest request) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(request.getName());
        ingredient.setDescription(request.getDescription());
        ingredient.setUom(request.getUom());

        return ingredientRepository.save(ingredient);
    }

    public Ingredient update(UpdateIngredientRequest request) {
        Ingredient ingredient = findOne(request.getIngredientId());
        ingredient.setName(request.getName());
        ingredient.setDescription(request.getDescription());
        ingredient.setUom(request.getUom());

        return ingredientRepository.save(ingredient);
    }

    public void delete(Long id) {

        Ingredient ingredient = findOne(id);
        recipeIngredientRepository.deleteAll(ingredient.getRecipeIngredients());

        ingredientRepository.deleteById(id);
    }



}
