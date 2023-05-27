package rs.example.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.example.recipes.model.Recipe;
import rs.example.recipes.model.Step;

@Repository
public interface StepRepository extends JpaRepository<Step, Long> {



}
