package ru.itmo.wp.web.page;

import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage extends Page {
    @Json
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    @Json
    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.findById(Long.parseLong(request.getParameter("userId"))));
    }

    @Json
    private void getCurrentUser(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") != null) {
            view.put("curUser", getUser());
        }
    }

    @Json
    private void switchAdmin(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        long targetUserId = Long.parseLong(request.getParameter("targetUserId"));
        long sourceUserId = getUser().getId();
        boolean admin = Boolean.parseBoolean(request.getParameter("admin"));

        userService.validateAdminUpdate(sourceUserId, targetUserId, admin);
        userService.updateAdmin(targetUserId, admin);
    }
}
