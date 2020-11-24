package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.form.NoticeForm;
import ru.itmo.wp.form.validator.NoticeFormValidator;
import ru.itmo.wp.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AddNoticePage extends Page {
    private final NoticeService noticeService;
    private final NoticeFormValidator noticeFormValidator;

    public AddNoticePage(NoticeService noticeService, NoticeFormValidator noticeFormValidator) {
        this.noticeService = noticeService;
        this.noticeFormValidator = noticeFormValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        if (binder.getTarget() == null) {
            return;
        }

        if (noticeFormValidator.supports(binder.getTarget().getClass())) {
            binder.addValidators(noticeFormValidator);
        }
    }

    @GetMapping("/add_notice")
    public String addNoticeGet(HttpSession httpSession, Model model) {
        if (getUser(httpSession) == null) {
            putMessage(httpSession, "You should be authorised");
            return "redirect:/";
        }

        model.addAttribute("noticeForm", new NoticeForm());

        return "AddNoticePage";
    }

    @PostMapping("/add_notice")
    public String addNoticePost(@Valid @ModelAttribute("noticeForm") NoticeForm noticeForm,
                                BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "AddNoticePage";
        }

        if (getUser(httpSession) == null) {
            putMessage(httpSession, "You should be authorised");
            return "redirect:/";
        }

        noticeService.addNotice(noticeForm);
        putMessage(httpSession, "Notice added successfully");
        return "redirect:/";
    }
}
