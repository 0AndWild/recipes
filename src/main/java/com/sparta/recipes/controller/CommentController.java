//package com.sparta.recipes.controller;
//
//import com.sparta.recipes.domain.Comments;
//import com.sparta.recipes.dto.CommentDto;
//import com.sparta.recipes.repository.CommentRepository;
//import com.sparta.recipes.service.CommentService;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//public class CommentController {
//
//    private final CommentService commentService;
//    private final CommentRepository commentRepository;
//
//    public CommentController(CommentService commentService, CommentRepository commentRepository) {
//        this.commentService = commentService;
//        this.commentRepository = commentRepository;
//    }
//
//    // 댓글 전체 조회
//    @GetMapping("/api/comments")
//    public List<Comments> getAllOfComments() {
//        return commentRepository.findAll();
//    }
//
//    // 레시피 상세 페이지 댓글 조회
//    @GetMapping("/api/comments/{recipeId}")
//    public List<Comments> getComments(@PathVariable Long recipeId) {
//        return commentRepository.findByRecipeIdOrderByCommentIdDesc(recipeId);
//    }
//
//    // 댓글 작성
//    @PostMapping("/api/comments")
//    public Long createComment(@RequestBody CommentDto commentDto) {
//        return commentService.createComment(commentDto);
//    }
//
//    // 댓글 수정
//    // controller 에서 사용자, 작성자 대조하는 경우
//    @PutMapping("/api/comments/{commentId}")
//    public Long editComment(/*@AuthenticationPrincipal userDetail, */@PathVariable Long commentId, @RequestBody CommentDto commentDto){
////        checkUsername(userDetail.getUsername(), commentDto.getUsername());
//        commentService.editComment(commentDto,commentId);
//        return commentDto.getCommentId();
//    }
//
//    // 댓글 삭제
//    @DeleteMapping("/api/comments/{commentId}")
//    public Long deleteComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto){
//        commentService.deleteComment(commentDto, commentId);
//        return commentId;
//    }
//
//    // 작성자 체크
////    public void checkUsername(@AuthenticationPrincipal userDetail, CommentDto commentDto) {
////        if (!userDetail.getUsername().equals(recipeDto.getUsername())) {
////            throw new Exception("해당 작성자가 아님");
////        }
////    }
//    //
//}
//
