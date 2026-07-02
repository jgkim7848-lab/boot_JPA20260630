package com.example.boot.service;

import com.example.boot.entity.Comment;
import com.example.boot.dto.CommentDTO;

public interface CommentService {

    default Comment convertDtoToEntity(CommentDTO commentDTO) {
        return Comment.builder()
                .cno(commentDTO.getCno())
                .bno(commentDTO.getBno())
                .writer(commentDTO.getWriter())
                .content(commentDTO.getContent())
                .build();
    }

    default CommentDTO convertEntityToDto(Comment comment) {
        return CommentDTO.builder()
                .cno(comment.getCno())
                .bno(comment.getBno())
                .writer(comment.getWriter())
                .content(comment.getContent())
                .regDate(comment.getRegDate())
                .modDate(comment.getModDate())
                .build();
    }
}
