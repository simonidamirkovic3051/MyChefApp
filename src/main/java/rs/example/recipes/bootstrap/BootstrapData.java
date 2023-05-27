package rs.example.recipes.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.example.recipes.model.*;
import rs.example.recipes.repository.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

@Component
public class BootstrapData implements CommandLineRunner {

    private static final int TEST_DATA_COUNT = 5;

    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final StepRepository stepRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final PasswordEncoder passwordEncoder;

    public BootstrapData(UserRepository userRepository, RecipeRepository recipeRepository, StepRepository stepRepository,
                         CategoryRepository categoryRepository, IngredientRepository ingredientRepository,
                         PasswordEncoder passwordEncoder, RecipeIngredientRepository recipeIngredientRepository) {
        this.userRepository = userRepository;
        this.recipeRepository = recipeRepository;
        this.stepRepository = stepRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading data...");

        Random rand = new Random();

        // Creating users
        User admin = new User();
        admin.setEmail("admin@gmail.com");
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin"));

        admin = userRepository.save(admin);

        // Recipes

        ArrayList<Category> categories = new ArrayList<>();
        for (int i = 0; i < TEST_DATA_COUNT; i++) {
            Category category = new Category();
            category.setName("Category" + i);
            category.setDescription("Description" + i);

            Category cat = categoryRepository.save(category);

            if (i % 2 == 0) {
                categories.add(cat);
            }
        }

        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (int i = 0; i < TEST_DATA_COUNT; i++) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName("Ingredient" + i);
            ingredient.setDescription("Description" + i);
            UnitOfMeasure[] unitOfMeasures = UnitOfMeasure.values();
            ingredient.setUom(unitOfMeasures[rand.nextInt(unitOfMeasures.length)]);

            Ingredient ing = ingredientRepository.save(ingredient);
            ingredients.add(ing);
        }

        ArrayList<Recipe> recipes = new ArrayList<>();
        for (int i = 0; i < TEST_DATA_COUNT; i++) {
            Recipe recipe = new Recipe();
            recipe.setName("Recept" + i);
            recipe.setCalories(580);
            recipe.setCategories(categories);
            recipe.setCoverPicUrl("RandomPicUrl");
            recipe.setPrepTime(20 + i);
            Difficulty[] difficulties = Difficulty.values();
            recipe.setDifficulty(difficulties[rand.nextInt(difficulties.length)]);
            recipe.setUser(admin);

            ArrayList<Step> steps = new ArrayList<>();

            Step step1 = new Step();
            step1.setTitle("Step" + i);
            step1.setDescription("Description" + i);
            step1.setImageUrl("RandomImageUrl");

            Step step2 = new Step();
            step2.setTitle("Step1" + i);
            step2.setDescription("Description1" + i);
            step2.setImageUrl("RandomImageUrl1");

            steps.add(step1);
            steps.add(step2);

            Recipe r = recipeRepository.save(recipe);
            recipes.add(r);
            step1.setRecipe(r);
            step2.setRecipe(r);

            stepRepository.saveAll(steps);
            recipeRepository.save(recipe);
        }

        for (Recipe r: recipes) {
            RecipeIngredient ri = new RecipeIngredient();
            ri.setRecipe(r);
            ri.setIngredient(ingredients.get(rand.nextInt(ingredients.size())));
            ri.setAmount(BigDecimal.valueOf(rand.nextLong(1000)));

            recipeIngredientRepository.save(ri);
        }

        admin.setFavorites(recipes.subList(1,3));
        userRepository.save(admin);


        System.out.println("Data loaded");

    }
}
