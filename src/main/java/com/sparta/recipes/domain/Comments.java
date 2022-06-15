package com.sparta.recipes.domain;

import com.sparta.recipes.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="Comments")
@NoArgsConstructor
@Getter
@Setter
public class Comments {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = false)
    private String commentContent;

    @Column(nullable = false)
    private Long recipeId;

    @Column(nullable = false)
    private String username;

    public Comments(CommentDto commentDto) {
        this.commentContent = commentDto.getCommentContent();
        this.recipeId = commentDto.getRecipeId();
        this.username = commentDto.getUsername();
    }

    // 댓글 수정
    public void updateComments(CommentDto commentDto) {
        this.commentContent = commentDto.getCommentContent();
    }
}