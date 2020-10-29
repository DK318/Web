package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public abstract class Page {
    protected final UserService userService = new UserService();
    protected final EventService eventService = new EventService();
    protected HttpServletRequest curRequest = null;
    protected HttpSession curSession = null;

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        curRequest = request;
        curSession = curRequest.getSession();
        view.put("userCount", userService.findCount());
        User user = getUser();
        if (user != null) {
            setUser(user, view);
        }
        String message = (String) curSession.getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            curSession.removeAttribute("message");
        }
    }

    protected void setUser(User user, Map<String, Object> view) {
        curSession.setAttribute("user", user);
        view.put("user", user);
    }

    protected User getUser() {
        return (User) curSession.getAttribute("user");
    }

    protected void setMessage(String message) {
        curSession.setAttribute("message", message);
        throw new RedirectException("/index");
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }
}
