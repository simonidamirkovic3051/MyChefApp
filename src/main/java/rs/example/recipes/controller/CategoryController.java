package rs.example.recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.example.recipes.model.Category;
import rs.example.recipes.request.CreateCategoryRequest;
import rs.example.recipes.request.UpdateCategoryRequest;
import rs.example.recipes.service.CategoryService;

@RestController
@CrossOrigin
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody CreateCategoryRequest request) {
        Category category = categoryService.create(request);
        return ResponseEntity.ok(category);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody UpdateCategoryRequest request) {
        Category category = categoryService.update(request);
        return ResponseEntity.ok(category);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
