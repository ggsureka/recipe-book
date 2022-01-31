package nl.abnamro.recipebook.integration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.abnamro.recipebook.dto.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@SpringBootTest
public class RecipeControllerIT {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    private MockMvc mockMvc;

    @BeforeEach
    public void applySecurity() {
        this.mockMvc = webAppContextSetup(wac)
                .apply(springSecurity(springSecurityFilterChain))
                .build();
    }

    @Test
    public void givenRecipes_whenGetAllRecipes_thenReturnJsonArray() throws Exception {
        mockMvc.perform(get("/api/allRecipes")
                        .with(httpBasic("user", "wordPass")))
                .andExpect(status().isOk());
    }


    @Test
    public void givenRecipes_whenGetAllRecipesWithWrongCredentials_thenReturn401() throws Exception {
        mockMvc.perform(get("/api/allRecipes")
                        .with(httpBasic("user", "pass")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenRecipe_whenPostRecipe_thenReturnCreatedJsonArray() throws Exception {
        Recipe recipe = new Recipe(1L, "pasta", null, "yes", 4, new ArrayList<>(), "test test test");

        mockMvc.perform(post("/api/createRecipe")
                        .with(httpBasic("user", "wordPass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(recipe)))
                .andExpect(status().isCreated());
    }


    @Test
    public void givenRecipes_whenPostRecipeWithWrongCredentials_thenReturn401() throws Exception {
        Recipe recipe = new Recipe(1L, "pasta", null, "yes", 4, new ArrayList<>(), "test test test");

        mockMvc.perform(post("/api/createRecipe")
                        .with(httpBasic("user", "pass"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(recipe)))
                .andExpect(status().isUnauthorized());
    }


    private static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
