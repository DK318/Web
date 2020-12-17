package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.RegisterForm;
import ru.itmo.wp.service.UserService;

@Component
public class RegisterFormValidator implements Validator {
    private final UserService userService;

    public RegisterFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RegisterForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (!errors.hasErrors()) {
            RegisterForm registerForm = (RegisterForm) o;
            if (userService.findByLogin(registerForm.getLogin()) != null) {
                errors.rejectValue("login", "login:in-use", "This login is in use");
            }
        }
    }
}
