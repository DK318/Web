package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class ArticlePage extends Page {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            request.getSession().setAttribute("message", "You should be logged in");
            throw new RedirectException("/index");
        }
    }

    @Json
    private void submit(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        Article article = new Article();

        long userId = getUser().getId();
        String title = request.getParameter("title");
        String text = request.getParameter("text");

        article.setUserId(userId);
        article.setTitle(title);
        article.setText(text);
        article.setHidden(false);
        articleService.validate(article);

        articleService.save(article);

        setMessage("You successfully posted your article");
    }
}
