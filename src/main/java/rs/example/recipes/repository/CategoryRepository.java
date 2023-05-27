package rs.example.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.example.recipes.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {



}
