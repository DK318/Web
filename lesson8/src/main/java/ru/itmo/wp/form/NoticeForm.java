package ru.itmo.wp.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("unused")
public class NoticeForm {
    @NotNull(message = "Content can't be empty")
    @NotEmpty(message = "Content can't be empty")
    @NotBlank(message = "Content can't be empty")
    @Size(min = 5, max = 250, message = "Content length should be between 5 and 250")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
