package ru.itmo.wp.model.service;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleService {
    ArticleRepository articleRepository = new ArticleRepositoryImpl();
    UserService userService = new UserService();

    public void validate(Article article) throws ValidationException {
        if (Strings.isNullOrEmpty(article.getTitle())) {
            throw new ValidationException("You should write a title");
        }
        if (Strings.isNullOrEmpty(article.getText())) {
            throw new ValidationException("You should write a text");
        }

        if (article.getTitle().length() < 4) {
            throw new ValidationException("Title length should be at least 4 symbols");
        }
        if (article.getTitle().length() > 20) {
            throw new ValidationException("Title length should be less than 20 symbols");
        }

        if (article.getText().length() < 4) {
            throw new ValidationException("Text length should be at least 4 symbols");
        }
        if (article.getText().length() > 1000) {
            throw new ValidationException("Text length should be less than 1000 symbols");
        }
    }

    public void validateUpdate(long userId, long articleId, boolean hidden) throws ValidationException {
        Article article = find(articleId);

        if (article.getUserId() != userId) {
            throw new ValidationException("You don't have permissions");
        }
        if (article.isHidden() == hidden) {
            throw new ValidationException("Article is already hidden/shown");
        }
    }

    public void updateHidden(long articleId, boolean hidden) {
        articleRepository.updateHidden(articleId, hidden);
    }

    public void save(Article article) {
        articleRepository.save(article);
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findAllByUserId(long userId) {
        return articleRepository.findAllByUserId(userId);
    }

    public List<ArticleView> findNotHidden() {
        List<Article> articles = articleRepository.findNotHidden();

        List<ArticleView> articleViews = new ArrayList<>();
        for (Article article : articles) {
            ArticleView cur = new ArticleView();

            cur.setUser(userService.findById(article.getUserId()));
            cur.setTitle(article.getTitle());
            cur.setText(article.getText());
            cur.setCreationTime(article.getCreationTime());

            articleViews.add(cur);
        }

        return articleViews;
    }

    public static class ArticleView {
        private User user;
        private String title;
        private String text;
        private Date creationTime;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public Date getCreationTime() {
            return creationTime;
        }

        public void setCreationTime(Date creationTime) {
            this.creationTime = creationTime;
        }
    }
}
