//package com.sparta.recipes.repository;
//
//import com.sparta.recipes.domain.Comments;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface CommentRepository extends JpaRepository<Comments, Long> {
//
//    // 댓글 전체 조회
//    List<Comments> findAllByOrderByCommentIdAsc();
//
//    // 레시피 상세 페이지 댓글 조회
//    List<Comments> findByRecipeIdOrderByCommentIdDesc(Long recipeId);
//
//    // 수정, 삭제용 댓글 조회
//
//    Comments findByCommentId(Long commentId);
//
//    List<Comments> findAllByOrderByRecipeIdDesc();
//}
