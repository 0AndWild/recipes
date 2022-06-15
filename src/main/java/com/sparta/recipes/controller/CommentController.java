package com.sparta.recipes.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    public CommentController(CommentService commentService, CommentRepository commentRepository) {
        this.commentService = commentService;
        this.commentRepository = commentRepository;
    }

    // 댓글 전체 조회
    @GetMapping("/api/comments")
    public List<Comments> getAllOfComments() {
        return commentRepository.findAll();
    }

    // 레시피 상세 페이지 댓글 조회
    @GetMapping("/api/comments/{recipeId}")
    public List<Comments> getComments(@PathVariable Long recipeId) {
        return commentRepository.findByRecipeIdOrderByCommentIdDesc(recipeId);
    }

    // 댓글 작성
    @PostMapping("/api/comments")
    public void createComment(@RequestBody CommentDto commentDto) {
        commentService.createComment(commentDto);
    }

    // 댓글 수정
    // controller 에서 사용자, 작성자 대조하는 경우
    @PutMapping("/api/comments/{commentId}")
    public void editComment(/*@AuthenticationPrincipal userDetail, */@PathVariable Long commentId, @RequestBody CommentDto commentDto){
//        checkUsername(userDetail.getUsername(), commentDto.getUsername());
        commentService.editComment(commentDto,commentId);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto){
        commentService.deleteComment(commentDto, commentId);
    }

    // 작성자 체크
//    public void checkUsername(@AuthenticationPrincipal userDetail, CommentDto commentDto) {
//        if (!userDetail.getUsername().equals(recipeDto.getUsername())) {
//            throw new Exception("해당 작성자가 아님");
//        }
//    }
    //
}

