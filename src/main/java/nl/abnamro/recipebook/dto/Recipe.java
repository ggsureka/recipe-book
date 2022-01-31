package nl.abnamro.recipebook.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @JsonFormat(pattern="dd‐MM‐yyyy HH:mm")
    @DateTimeFormat(pattern="dd‐MM‐yyyy HH:mm")
    private LocalDateTime creationDate;

    private String vegetarian;

    private int noOfPersons;

    @OneToMany(cascade=CascadeType.ALL)
    private List<Ingredient> ingredients;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String instructions;

    public Recipe(String name, LocalDateTime creationDate, String vegetarian, int noOfPersons, String instructions){
        this.name = name;
        this.creationDate = creationDate;
        this.vegetarian = vegetarian;
        this.noOfPersons = noOfPersons;
        this.instructions = instructions;
    }

}
