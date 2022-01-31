package nl.abnamro.recipebook.service;

import lombok.extern.slf4j.Slf4j;
import nl.abnamro.recipebook.dto.Recipe;
import nl.abnamro.recipebook.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public List<Recipe> getAllRecipes(){
        return recipeRepository.findAll();
    }

    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public String deleteRecipe(String name) {
        Recipe tobeDeletedRecipe= recipeRepository.findByName(name);
        recipeRepository.delete(tobeDeletedRecipe);
        return "Recipe is deleted";
    }

    public Recipe updateRecipe(Recipe recipe) {
        Recipe toBeUpdatedRecipe = recipeRepository.findByName(recipe.getName());
        toBeUpdatedRecipe.setVegetarian(recipe.getVegetarian());
        toBeUpdatedRecipe.setNoOfPersons(recipe.getNoOfPersons());
        toBeUpdatedRecipe.setInstructions(recipe.getInstructions());
        toBeUpdatedRecipe.setIngredients(recipe.getIngredients());
        toBeUpdatedRecipe.setCreationDate(recipe.getCreationDate());
        return recipeRepository.save(toBeUpdatedRecipe);
    }

    public List<Recipe> retrieveRecipes(Map<String, String> attributes) {
        boolean vegetarian = Boolean.parseBoolean(attributes.getOrDefault("vegetarian", "false"));
        int noOfPersons = Integer.parseInt(attributes.getOrDefault("noOfPersons","0"));
        String name = attributes.get("name");
        return recipeRepository.findByAttributes(vegetarian, noOfPersons, name );
    }

}
