package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.BadIdException;
import ru.itmo.wp.exception.BadJwtException;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class PostController extends ApiController {
    private final PostService postService;
    private final JwtService jwtService;

    public PostController(PostService postService, JwtService jwtService) {
        this.postService = postService;
        this.jwtService = jwtService;
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }

    @GetMapping("posts/{id}")
    public Post findPost(@PathVariable String id) {
        try {
            return postService.find(Long.parseLong(id));
        } catch (NumberFormatException e) {
            throw new BadIdException("Bad post id");
        }
    }

    @PostMapping("posts")
    public void createPost(@RequestBody @Valid PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        User user = jwtService.find(postForm.getJwt());
        if (user == null) {
            throw new BadJwtException("User not found");
        }

        postService.createPost(postForm, user);
    }
}
