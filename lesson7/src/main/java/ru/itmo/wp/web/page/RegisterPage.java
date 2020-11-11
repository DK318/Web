package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class RegisterPage extends Page {
    @Json
    private void register(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        user.setEmail(request.getParameter("email"));
        user.setAdmin(false);
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        userService.validateRegistration(user, password, confirmPassword);
        userService.register(user, password);

        setMessage("You are successfully registered!");
    }
}
