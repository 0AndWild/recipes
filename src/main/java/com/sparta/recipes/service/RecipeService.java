package com.sparta.recipes.service;

import com.sparta.recipes.domain.Recipes;
import com.sparta.recipes.dto.RecipeDto;
import com.sparta.recipes.repository.RecipeRepository;
import com.sparta.recipes.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // 레시피 Create
    @Transactional
    public Long createRecipe(RecipeDto recipeDto, String username) {
        Recipes recipes = new Recipes(recipeDto, username);
        recipeRepository.save(recipes);
        List<Recipes> tempList = recipeRepository.findAllByOrderByRecipeIdAsc();
        return tempList.get(tempList.size()-1).getRecipeId();
    }

    // 레시피 SELECT recipeId FROM Recipes WHERE ..
    @Transactional
    public Recipes selectRecipe(Long recipeId) {
        checkRecipeId(recipeId);
        return recipeRepository.findByRecipeId(recipeId);
    }

    // 레시피 Update
    @Transactional
    public Long editRecipe(Long recipeId, RecipeDto recipeDto, UserDetailsImpl userDetails) {


        Recipes recipes = recipeRepository.findByRecipeId(recipeId);
        String checkName = recipes.getUsername();
        String username = userDetails.getUser().getUsername();
        if (checkName.equals(username)){
            recipes.updateRecipe(recipeDto);
            return 1L;
        } else {
            return 0L;
        }

    }

    // 레시피 Delete
    @Transactional
    public Boolean deleteRecipe(Long recipeId, UserDetailsImpl userDetails) {
        Recipes recipes = recipeRepository.findByRecipeId(recipeId);
        String checkName = recipes.getUsername();
        String username = userDetails.getUser().getUsername();

        if(checkName.equals(username)){
            recipeRepository.deleteById(recipeId);
            return true;
        } return false;

    }


    // 레시피 ID 체크
    public void checkRecipeId(Long recipeId) {
        if (recipeRepository.findByRecipeId(recipeId) == null) {
            throw new NullPointerException("RecipeId isn't exist.");
        }
    }

}