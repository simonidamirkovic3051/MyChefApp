package rs.example.recipes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.example.recipes.model.Category;
import rs.example.recipes.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {



}
