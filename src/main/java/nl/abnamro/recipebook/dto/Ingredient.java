package nl.abnamro.recipebook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ingredientId;

    private String name;

    private double amount;

    public Ingredient(String name, double amount){
        this.name = name;
        this.amount = amount;
    }
}
