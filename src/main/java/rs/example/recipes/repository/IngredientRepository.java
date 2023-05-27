package rs.example.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.example.recipes.model.Ingredient;
import rs.example.recipes.model.Step;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {



}
