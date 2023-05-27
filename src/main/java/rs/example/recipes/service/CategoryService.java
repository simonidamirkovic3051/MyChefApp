package rs.example.recipes.service;

import org.springframework.stereotype.Service;
import rs.example.recipes.exception.CategoryNotFoundException;
import rs.example.recipes.model.Category;
import rs.example.recipes.model.Recipe;
import rs.example.recipes.repository.CategoryRepository;
import rs.example.recipes.request.CreateCategoryRequest;
import rs.example.recipes.request.UpdateCategoryRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findOne(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    public Category create(CreateCategoryRequest request) {

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    public Category update(UpdateCategoryRequest request) {

        Category category = findOne(request.getId());
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        Category category = findOne(id);

        ArrayList<Recipe> recipes = new ArrayList<>(category.getRecipes());

        for (Recipe recipe: recipes) {
            category.deleteRecipe(recipe);
        }

        categoryRepository.deleteById(id);
    }

}
