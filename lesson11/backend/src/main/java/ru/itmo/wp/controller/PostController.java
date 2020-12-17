package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.BadIdException;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.validator.PostFormValidator;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.PostService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class PostController extends ApiController {
    private final PostService postService;
    private final PostFormValidator postFormValidator;

    public PostController(PostService postService, PostFormValidator postFormValidator) {
        this.postService = postService;
        this.postFormValidator = postFormValidator;
    }

    @InitBinder("postForm")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(postFormValidator);
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

        postService.createPost(postForm);
    }
}
