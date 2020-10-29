package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class LogoutPage extends Page {
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        super.action(request, view);
        User user = (User) curSession.getAttribute("user");
        curSession.removeAttribute("user");

        Event event = new Event();
        event.setUserId(user.getId());
        event.setType(Event.TYPE.LOGOUT);
        eventService.register(event);

        setMessage("Good bye. Hope to see you soon!");
    }
}
