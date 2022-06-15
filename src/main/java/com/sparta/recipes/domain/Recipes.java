package com.sparta.recipes.domain;

import com.sparta.recipes.dto.RecipeDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="Recipes")
@NoArgsConstructor
public class Recipes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String recipeTitle;

    @Column(nullable = false)
    private String recipeContent;

    @Column(nullable = false)
    private String recipeImgUrl;

    public Recipes(RecipeDto recipeDto) {
        this.username = recipeDto.getUsername();
        this.recipeTitle = recipeDto.getRecipeTitle();
        this.recipeContent = recipeDto.getRecipeContent();
        this.recipeImgUrl =  recipeDto.getRecipeImgUrl();
    }

    public void updateRecipe(RecipeDto recipeDto) {
        this.recipeTitle = recipeDto.getRecipeTitle();
        this.recipeContent = recipeDto.getRecipeContent();
        this.recipeImgUrl = recipeDto.getRecipeImgUrl();
    }


}
