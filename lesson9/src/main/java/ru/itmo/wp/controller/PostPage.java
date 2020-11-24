package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.security.Guest;
import ru.itmo.wp.service.PostService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {
    private final PostService postService;

    public PostPage(PostService postService) {
        this.postService = postService;
    }

    @Guest
    @GetMapping("/post/{id}")
    public String postGet(@PathVariable String id, HttpSession httpSession, Model model) {
        Post post;
        try {
            post = postService.find(Long.parseLong(id));
        } catch (NumberFormatException e) {
            putMessage(httpSession, "Post not found");
            return "redirect:/";
        }

        if (post == null) {
            putMessage(httpSession, "Post not found");
            return "redirect:/";
        }

        model.addAttribute("post", post);
        model.addAttribute("comment", new Comment());

        return "PostPage";
    }

    @PostMapping("/post/{id}")
    public String postPost(@Valid @ModelAttribute("comment") Comment comment,
                           BindingResult bindingResult,
                           @PathVariable long id,
                           HttpSession httpSession,
                           Model model) {
        Post post = postService.find(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);
            return "PostPage";
        }

        postService.writeComment(comment, post, getUser(httpSession));

        return "redirect:/post/" + id;
    }
}
