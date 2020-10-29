package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class EnterPage extends Page {

    private void enter(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String loginOrEmail = request.getParameter("loginOrEmail");
        String password = request.getParameter("password");

        userService.validateEnter(loginOrEmail, password);
        User user = userService.findByLoginAndPassword(loginOrEmail, password);
        if (user == null) {
            user = userService.findByEmailAndPassword(loginOrEmail, password);
        }

        Event event = new Event();
        event.setUserId(user.getId());
        event.setType(Event.TYPE.ENTER);
        eventService.register(event);

        setUser(user, view);

        setMessage("Hello, " + user.getLogin());
    }
}
