package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.form.PostForm;

@Component
public class PostFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return PostForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (!errors.hasErrors()) {
            PostForm postForm = (PostForm) o;

            String tags = postForm.getTags();
            if (tags == null || tags.isEmpty()) {
                return;
            }

            String[] tagArray = tags.split(" ");
            if (tagArray.length == 0) {
                return;
            }

            for (String tag : tagArray) {
                if (!tag.matches("[a-z]+")) {
                    errors.rejectValue("tags", "tags.bad-tag",
                            "Tag " + tag + " must contain only lowercase Latin letters");
                    return;
                }
            }
        }
    }
}
