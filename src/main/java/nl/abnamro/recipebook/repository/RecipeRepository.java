package nl.abnamro.recipebook.repository;

import nl.abnamro.recipebook.dto.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    Recipe findByName(String name);

    Optional<Recipe> findById(Long id);

    @Query("SELECT r FROM Recipe r WHERE r.vegetarian = ?1 or r.noOfPersons = ?2 or  r.name = ?3")
    List<Recipe> findByAttributes(boolean vegetarian, int noOfPersons, String name );
}
