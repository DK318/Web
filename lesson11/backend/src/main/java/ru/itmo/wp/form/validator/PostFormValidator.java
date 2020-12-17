package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.service.JwtService;

@Component
public class PostFormValidator implements Validator {
    private final JwtService jwtService;

    public PostFormValidator(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PostForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }

        PostForm postForm = (PostForm) o;

        User user = jwtService.find(postForm.getJwt());
        if (user == null) {
            errors.rejectValue("jwt", "jwt:bad-token", "Can't find user with that token");
        }
    }
}
