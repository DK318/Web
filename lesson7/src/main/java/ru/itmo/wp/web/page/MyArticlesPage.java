package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class MyArticlesPage extends Page {
    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        if (getUser() == null) {
            setMessage("You should be logged in");
        }
        User curUser = getUser();
        view.put("articles", articleService.findAllByUserId(curUser.getId()));
    }

    @Json
    private void hideOrShowArticle(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long articleId = Long.parseLong(request.getParameter("articleId"));
        boolean hidden = Boolean.parseBoolean(request.getParameter("hidden"));
        long userId = getUser().getId();

        articleService.validateUpdate(userId, articleId, hidden);
        articleService.updateHidden(articleId, hidden);
    }
}
