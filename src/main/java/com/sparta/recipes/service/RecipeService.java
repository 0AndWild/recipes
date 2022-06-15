package com.sparta.recipes.service;

import com.sparta.recipes.domain.Recipes;
import com.sparta.recipes.dto.RecipeDto;
import com.sparta.recipes.repository.RecipeRepository;
import com.sparta.recipes.security.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public void editRecipe(RecipeDto recipeDto, Long recipeId, String username) {
        checkRecipeId(recipeId);
//      checkUsername(recipeDto.getUsername());
        Recipes recipes = recipeRepository.findByRecipeId(recipeId);
        editRecipeVal(recipeDto);
        recipes.updateRecipe(recipeDto, username);
    }

    // 레시피 Delete
    @Transactional
    public void deleteRecipe(Long recipeId, String username) {
        checkRecipeId(recipeId);
//      checkUsername(username);
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
        if(recipeDto.getTitle().isEmpty()){
            recipeDto.setTitle(recipeRepository.findByRecipeId(recipeDto.getRecipeId()).getTitle());
        }
        if(recipeDto.getImage().isEmpty()){
            recipeDto.setImage(recipeRepository.findByRecipeId(recipeDto.getRecipeId()).getImage());
        }
        if(recipeDto.getContents().isEmpty()){
            recipeDto.setContents(recipeRepository.findByRecipeId(recipeDto.getRecipeId()).getContents());
        }
    }

    // 작성자 체크
//    public void checkUsername(String username) {
//        if (!username.equals(recipeDto.getUsername())) {
//            throw new Exception("해당 작성자가 아님");
//        }
//    }
}