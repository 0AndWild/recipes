package com.sparta.recipes.service;

import com.sparta.recipes.domain.Recipes;
import com.sparta.recipes.dto.RecipeDto;
import com.sparta.recipes.repository.RecipeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // 레시피 Create
    @Transactional
    public void createRecipe(RecipeDto recipeDto) {
        Recipes recipes = new Recipes(recipeDto);
        recipeRepository.save(recipes);
    }

    // 레시피 SELECT recipeId FROM Recipes WHERE ..
    @Transactional
    public Recipes selectRecipe(Long recipeId) {
        checkRecipeId(recipeId);
        return recipeRepository.findByRecipeId(recipeId);
    }

    // 레시피 Update
    @Transactional
    public void editRecipe(RecipeDto recipeDto, Long recipeId) {
        checkRecipeId(recipeId);
//      checkUsername(recipeDto.getUsername());
        Recipes recipes = recipeRepository.findByRecipeId(recipeId);
        editRecipeVal(recipeDto);
        recipes.updateRecipe(recipeDto);
    }

    // 레시피 Delete
    @Transactional
    public void deleteRecipe(Long recipeId) {
        checkRecipeId(recipeId);
//      checkUsername(recipeDto.getUsername());
        recipeRepository.deleteById(recipeId);
    }


    // 레시피 ID 체크
    public void checkRecipeId(Long recipeId) {
        if (recipeRepository.findByRecipeId(recipeId) == null) {
            throw new NullPointerException("RecipeId isn't exist.");
        }
    }

    // 레시피 수정 시 빈 칸은 수정 없는 칸으로
    public void editRecipeVal(RecipeDto recipeDto){
        if(recipeDto.getRecipeTitle().isEmpty()){
            recipeDto.setRecipeTitle(recipeRepository.findByRecipeId(recipeDto.getRecipeId()).getRecipeTitle());
        }
        if(recipeDto.getRecipeImgUrl().isEmpty()){
            recipeDto.setRecipeImgUrl(recipeRepository.findByRecipeId(recipeDto.getRecipeId()).getRecipeImgUrl());
        }
        if(recipeDto.getRecipeContent().isEmpty()){
            recipeDto.setRecipeContent(recipeRepository.findByRecipeId(recipeDto.getRecipeId()).getRecipeContent());
        }
    }

    // 작성자 체크
//    public void checkUsername(@AuthenticationPrincipal userDetail) {
//        if (!userDetail.getUsername().equals(recipeDto.getUsername())) {
//            throw new Exception("해당 작성자가 아님");
//        }
//    }
}