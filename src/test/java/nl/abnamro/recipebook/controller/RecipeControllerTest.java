package nl.abnamro.recipebook.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.abnamro.recipebook.dto.Recipe;
import nl.abnamro.recipebook.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebMvcTest
class RecipeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    RecipeService recipeService;

    @Test
    @WithMockUser
    public void givenRecipes_whenGetAllRecipes_thenReturnJsonArray() throws Exception {
        Recipe recipe = new Recipe(1L, "pasta", null, "yes", 4, new ArrayList<>(), "test test test");

        List<Recipe> allRecipes = List.of(recipe);

        when(recipeService.getAllRecipes()).thenReturn(allRecipes);

        ResultActions result  =  mvc.perform(get("/api/allRecipes")
                        .contentType(MediaType.APPLICATION_JSON));
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is(recipe.getName())));
    }


    @Test
    public void givenRecipe_whenPostRecipe_thenReturnJsonArray()
            throws Exception {
        Recipe recipe = new Recipe(1L, "pasta", null, "yes", 4, new ArrayList<>(), "test test test");

        when(recipeService.createRecipe(any(Recipe.class))).thenReturn(recipe);

        mvc.perform(post("/api/createRecipe")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(recipe)))
                        .andExpect(status().isCreated());

    }

    @Test
    @WithMockUser
    public void givenRecipes_whenDeleteRecipe_thenReturnString()
            throws Exception {
        when(recipeService.deleteRecipe(any(String.class))).thenReturn("Recipe is deleted");

        MvcResult requestResult = mvc.perform(delete("/api/deleteRecipe/pizza"))
                .andExpect(status().isOk()).andReturn();
        String result = requestResult.getResponse().getContentAsString();
        assertEquals(result, "Recipe is deleted");

    }

    private static byte[] toJson(Object object) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
