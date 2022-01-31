package nl.abnamro.recipebook.service;

import nl.abnamro.recipebook.dto.Recipe;
import nl.abnamro.recipebook.repository.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeService recipeService;

   @Test
    public void testCreateRecipe(){
        Recipe recipe = new Recipe(1L, "pasta", null, "yes", 4, new ArrayList<>(), "test test test");
        when(recipeRepository.save(any())).thenReturn(recipe);
        Recipe recipe1 = recipeService.createRecipe(recipe);
        assertEquals("pasta", recipe1.getName());
    }

    @Test
    public void testAllRecipes(){
        Recipe recipe = new Recipe(1L, "pasta", null, "yes", 4, new ArrayList<>(), "test test test");

        List<Recipe> allRecipes = List.of(recipe);

        when(recipeRepository.findAll()).thenReturn(allRecipes);
        List<Recipe>  recipeList = recipeService.getAllRecipes();
        assertEquals("pasta", recipeList.get(0).getName());
    }

    @Test
    public void testDeleteRecipe(){
        Recipe recipe = new Recipe(1L, "pasta", null, "yes", 4, new ArrayList<>(), "test test test");
        when(recipeRepository.findByName("pasta")).thenReturn(recipe);
        String  recipeList = recipeService.deleteRecipe("pasta");
        assertEquals("Recipe is deleted", recipeList);
    }

}