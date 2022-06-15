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
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String username;

    public Recipes(RecipeDto recipeDto) {
        this.title = recipeDto.getTitle();
        this.contents = recipeDto.getContents();
        this.image =  recipeDto.getImage();
    }

    public Recipes(RecipeDto recipeDto, String username){
        this.title = recipeDto.getTitle();
        this.contents = recipeDto.getContents();
        this.image = recipeDto.getImage();
        this.username = username;
    }

    public void updateRecipe(RecipeDto recipeDto, String username) {
        this.title = recipeDto.getTitle();
        this.contents = recipeDto.getContents();
        this.image = recipeDto.getImage();
        this.username = username;
    }


}
