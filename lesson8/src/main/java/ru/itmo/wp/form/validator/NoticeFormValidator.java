package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.NoticeForm;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
public class NoticeFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return NoticeForm.class.equals(aClass);
    }

    private Set<ConstraintViolation<NoticeForm>> revalidate(NoticeForm noticeForm) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        javax.validation.Validator defaultValidator = factory.getValidator();

        return defaultValidator.validate(noticeForm);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (!errors.hasErrors()) {
            NoticeForm noticeForm = (NoticeForm) o;

            String content = noticeForm.getContent().trim();
            noticeForm.setContent(content);
            Set<ConstraintViolation<NoticeForm>> newErrors = revalidate(noticeForm);

            if (!newErrors.isEmpty()) {
                for (ConstraintViolation<NoticeForm> constraintViolation : newErrors) {
                    errors.rejectValue("content", "content.validation-error",
                            constraintViolation.getMessage());
                }
            }
        }
    }
}
