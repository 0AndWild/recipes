package com.sparta.recipes.controller;


import com.sparta.recipes.domain.Recipes;
import com.sparta.recipes.domain.Users;
import com.sparta.recipes.dto.RecipeDto;
import com.sparta.recipes.repository.RecipeRepository;
import com.sparta.recipes.security.UserDetailsImpl;
import com.sparta.recipes.service.RecipeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
// @RequestMapping("/api")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final RecipeService recipeService;

    public RecipeController(RecipeRepository recipeRepository, RecipeService recipeService) {
        this.recipeRepository = recipeRepository;
        this.recipeService = recipeService;
    }

    // 레시피 전체 조회
    @GetMapping("/api/board")
    public List<Recipes> giveAllOfRecipes() {
//        System.out.println(recipeRepository.findAllByOrderByRecipeIdAsc());
        return recipeRepository.findAllByOrderByRecipeIdAsc();
    }

    // 레시피 수정
    @PutMapping("/api/board/{recipeId}")
    public Long editRecipe(@PathVariable Long recipeId, @RequestBody RecipeDto recipeDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Users users = (Users) authentication.getPrincipal();
//        String detailName = users.getUsername();

        if(recipeService.editRecipe(recipeId,recipeDto,userDetails) == 1L){
            return 1L;
        } else {
            return 0L;
        }

    }

    // 레시피 삭제
    @DeleteMapping("/api/board/{recipeId}")
    public Long deleteRecipe(@PathVariable Long recipeId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(recipeService.deleteRecipe(recipeId,userDetails)){
            return 1L;
        } else {
            return 0L;
        }
    }

    // 레시피 작성
    @PostMapping("/api/board/write")
    public Long createRecipe(@RequestBody RecipeDto recipeDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return recipeService.createRecipe(recipeDto, userDetails.getUsername());
    }

    // 레시피 상세 조회
    @GetMapping("/api/board/{recipeId}")
    public Recipes giveRecipe(@PathVariable Long recipeId) {
        return recipeService.selectRecipe(recipeId);
    }
}
