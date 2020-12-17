package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.service.JwtService;

@Component
public class CommentFormValidator implements Validator {
    private final JwtService jwtService;

    public CommentFormValidator(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CommentForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        CommentForm commentForm = (CommentForm) o;

        User user = jwtService.find(commentForm.getJwt());
        if (user == null) {
            errors.rejectValue("jwt", "jwt:bad-token", "Can't find user with that token");
        }
    }
}
