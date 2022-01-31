package nl.abnamro.recipebook.controller;

import nl.abnamro.recipebook.dto.Recipe;
import nl.abnamro.recipebook.service.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecipeController {
    private RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping(path = "/allRecipes", produces ="application/json")
    private ResponseEntity<List<Recipe>> allRecipes(){
        List<Recipe> recipes = recipeService.getAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    @GetMapping("/retrieveRecipes")
    private List<Recipe> retrieveRecipes(@RequestParam Map<String, String> attributes){
        return recipeService.retrieveRecipes(attributes);
    }

    @PostMapping("/createRecipe")
    private ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe){
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd‐MM‐yyyy HH:mm");
        String text = date.format(formatters);
        LocalDateTime parsedDate = LocalDateTime.parse(text, formatters);
        recipe.setCreationDate(parsedDate);

        Recipe newRecipe = recipeService.createRecipe(recipe);
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }

    @PutMapping("/updateRecipe")
    private ResponseEntity<Recipe> updateRecipe(@RequestBody Recipe recipe){
        //set updated date
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd‐MM‐yyyy HH:mm");
        String text = date.format(formatters);
        LocalDateTime parsedDate = LocalDateTime.parse(text, formatters);
        recipe.setCreationDate(parsedDate);
        Recipe updatedRecipe = recipeService.updateRecipe(recipe);
        return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
    }

    @DeleteMapping ("/deleteRecipe/{name}")
    private ResponseEntity<String> deleteRecipe(@PathVariable("name") String name){

        String response = recipeService.deleteRecipe(name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
