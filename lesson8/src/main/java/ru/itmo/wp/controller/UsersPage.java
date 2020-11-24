package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.DisabledForm;
import ru.itmo.wp.form.validator.DisabledFormValidator;
import ru.itmo.wp.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UsersPage extends Page {
    private final UserService userService;
    private final DisabledFormValidator disabledFormValidator;

    public UsersPage(UserService userService, DisabledFormValidator disabledFormValidator) {
        this.userService = userService;
        this.disabledFormValidator = disabledFormValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) {
            return;
        }

        if (disabledFormValidator.supports(binder.getTarget().getClass())) {
            binder.addValidators(disabledFormValidator);
        }
    }

    @ModelAttribute("users")
    public List<User> putUsers() {
        return userService.findAll();
    }

    @GetMapping("/users/all")
    public String users(Model model) {
        model.addAttribute("disabledForm", new DisabledForm());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String usersPost(@Valid @ModelAttribute("disabledForm") DisabledForm disabledForm,
                            BindingResult bindingResult, HttpSession httpSession) {
        if (!bindingResult.hasErrors()) {
            if (getUser(httpSession) == null) {
                putMessage(httpSession, "You should be authorised to switch users");
                return "redirect:/";
            }

            boolean disabledValue = !Boolean.parseBoolean(disabledForm.getDisabled());
            String login = disabledForm.getLogin();
            userService.setDisabled(disabledValue, login);
            putMessage(httpSession, login + " is now " + (disabledValue ? "disabled" : "enabled"));
            return "redirect:/users/all";
        }
        return "UsersPage";
    }
}
