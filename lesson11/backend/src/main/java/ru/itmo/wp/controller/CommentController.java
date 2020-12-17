package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.BadIdException;
import ru.itmo.wp.exception.BadJwtException;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final JwtService jwtService;

    public CommentController(CommentService commentService, PostService postService, JwtService jwtService) {
        this.commentService = commentService;
        this.postService = postService;
        this.jwtService = jwtService;
    }

    @GetMapping("comments")
    public List<Comment> getComments(@RequestParam String postId) {
        try {
            return postService.getComments(Long.parseLong(postId));
        } catch (NumberFormatException e) {
            throw new BadIdException("Bad post id");
        }
    }

    @PostMapping("comments/{id}")
    public void addComment(@RequestBody @Valid CommentForm commentForm,
                           BindingResult bindingResult,
                           @PathVariable String id) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        User user = jwtService.find(commentForm.getJwt());
        if (user == null) {
            throw new BadJwtException("User not found");
        }

        try {
            commentService.saveComment(commentForm, postService.find(Long.parseLong(id)));
        } catch (NumberFormatException e) {
            throw new BadIdException("Bad post id");
        }
    }
}
