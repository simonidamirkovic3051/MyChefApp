package rs.example.recipes.service;

import org.springframework.stereotype.Service;
import rs.example.recipes.exception.RecipeNotFoundException;
import rs.example.recipes.model.*;
import rs.example.recipes.repository.*;
import rs.example.recipes.request.AddCommentRequest;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final StepRepository stepRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final IngredientRepository ingredientRepository;
    private final CommentRepository commentRepository;
    private final EntityManager entityManager;

    public RecipeService(RecipeRepository recipeRepository, StepRepository stepRepository, RecipeIngredientRepository recipeIngredientRepository,
                         IngredientRepository ingredientRepository, CommentRepository commentRepository, EntityManager entityManager) {
        this.recipeRepository = recipeRepository;
        this.stepRepository = stepRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.ingredientRepository = ingredientRepository;
        this.commentRepository = commentRepository;
        this.entityManager = entityManager;
    }

    public Recipe findOne(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));
    }

    public List<Recipe> findAll() {
        return recipeRepository.findAll();
    }

    public List<Recipe> findByCriteria(String name, Difficulty difficulty, Integer prepTime, String[] categories) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> criteriaQuery = cb.createQuery(Recipe.class);
        Root<Recipe> root = criteriaQuery.from(Recipe.class);

        List<Predicate> searchCriterias = new ArrayList<>();

        if (name != null) {
            searchCriterias.add(cb.equal(root.get("name"), name));
        }
        if (difficulty != null) {
            searchCriterias.add(cb.equal(root.get("difficulty"), difficulty));
        }
        if (prepTime != null) {
            searchCriterias.add(cb.lessThanOrEqualTo(root.get("prepTime"), prepTime));
        }

        criteriaQuery.select(root).where(cb.and(searchCriterias.toArray(new Predicate[searchCriterias.size()])));
        ArrayList<Recipe> result = (ArrayList<Recipe>) entityManager.createQuery(criteriaQuery).getResultList();

        if (categories == null) {
            return result;
        }

        return result.stream()
                .filter(r -> {
                    List<String> categoryNames = r.getCategories().stream()
                            .map(Category::getName)
                            .toList();
                    return !Collections.disjoint(Arrays.asList(categories), categoryNames);
                })
                .toList();

    }

    public List<Recipe> findAllForUser(User user) {
        return recipeRepository.findAllByUser(user);
    }

    public List<Recipe> findFavorites(User user) {
        return user.getFavorites();
    }

    public Recipe create(User user, Recipe recipe) {

        recipe.setUser(user);
        ArrayList<Step> steps = (ArrayList<Step>) recipe.getSteps();
        ArrayList<RecipeIngredient> recipeIngredients = (ArrayList<RecipeIngredient>) recipe.getRecipeIngredients();

        recipe = recipeRepository.save(recipe);

        if (steps != null) {
            for (Step step: steps) {
                step.setRecipe(recipe);
                stepRepository.save(step);
            }
        }

        if (recipeIngredients != null) {
            for (RecipeIngredient recipeIngredient: recipeIngredients) {
                recipeIngredient.setRecipe(recipe);
                Optional<Ingredient> ingredientOptional = ingredientRepository
                        .findById(recipeIngredient.getId().getIngredientId());
                ingredientOptional.ifPresent(recipeIngredient::setIngredient);
                recipeIngredientRepository.save(recipeIngredient);
            }
        }

        return recipe;
    }

    public Recipe update(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void delete(Long id) {

        Recipe recipe = findOne(id);
        ArrayList<Category> categories = new ArrayList<>(recipe.getCategories());

        for (Category category: categories) {
            category.deleteRecipe(recipe);
        }

        recipeIngredientRepository.deleteAll(recipe.getRecipeIngredients());
        stepRepository.deleteAll(recipe.getSteps());
        recipe.getUser().getFavorites().remove(recipe);

        recipeRepository.deleteById(id);
    }

    public Recipe addToFavorites(User user, Long recipeId) {

        Recipe recipe = findOne(recipeId);
        recipe.getUsers().add(user);
        user.getFavorites().add(recipe);

        return recipeRepository.save(recipe);
    }

    public void removeFromFavorites(User user, Long recipeId) {

        Recipe recipe = findOne(recipeId);
        recipe.getUsers().remove(user);
        user.getFavorites().remove(recipe);

        recipeRepository.save(recipe);
    }

    public Comment addComment(AddCommentRequest request, User user) {
        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setRecipe(findOne(request.getRecipeId()));
        comment.setAuthor(user);

        return commentRepository.save(comment);
    }


}
