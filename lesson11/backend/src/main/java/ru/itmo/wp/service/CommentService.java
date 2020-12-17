package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.repository.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final JwtService jwtService;

    public CommentService(CommentRepository commentRepository, JwtService jwtService) {
        this.commentRepository = commentRepository;
        this.jwtService = jwtService;
    }

    public void saveComment(CommentForm commentForm, Post post) {
        Comment comment = new Comment();

        comment.setText(commentForm.getText());
        comment.setUser(jwtService.find(commentForm.getJwt()));
        comment.setPost(post);

        commentRepository.save(comment);
    }
}
