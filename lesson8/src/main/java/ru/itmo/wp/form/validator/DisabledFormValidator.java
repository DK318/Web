package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.DisabledForm;
import ru.itmo.wp.service.UserService;

@Component
public class DisabledFormValidator implements Validator {
    private final UserService userService;

    public DisabledFormValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return DisabledForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (!errors.hasErrors()) {
            DisabledForm disabledForm = (DisabledForm) o;

            User senderUser = userService.findByLogin(disabledForm.getSenderLogin());
            if (senderUser.isDisabled()) {
                errors.rejectValue("senderLogin", "senderLogin.user-is-disabled",
                        "Can't change user status because you are disabled");
                return;
            }

            String disabledString = disabledForm.getDisabled();

            if (!disabledString.equals("true") && !disabledString.equals("false")) {
                errors.rejectValue("disabled", "disabled.bad-value",
                        "Disabled value can't be " + disabledString);
                return;
            }

            boolean setValue = !Boolean.parseBoolean(disabledString);
            boolean userDisabledValue;

            try {
                userDisabledValue = userService.findByLogin(disabledForm.getLogin()).isDisabled();
            } catch (NullPointerException e) {
                errors.rejectValue("login", "login.user-not-found",
                        disabledForm.getLogin() + " is not exists");
                return;
            }

            if (setValue == userDisabledValue) {
                errors.rejectValue("disabled", "disabled.equal-values",
                        "This user is actually " + (setValue ? "enabled" : "disabled"));
            }
        }
    }
}
