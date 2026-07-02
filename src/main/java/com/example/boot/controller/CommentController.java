package com.example.boot.controller;

import com.example.boot.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
}
