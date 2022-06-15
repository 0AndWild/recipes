package com.sparta.recipes.repository;

import com.sparta.recipes.domain.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipes, Long> {

    List<Recipes> findAllByOrderByRecipeIdAsc();

    Recipes findByRecipeId(Long recipeId);
}
