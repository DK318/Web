package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class TalksPage extends Page {
    private final TalkService talkService = new TalkService();

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        if (getUser() == null) {
            setMessage("You should be authorised");
        }
        view.put("users", userService.findAll());
        List<Talk> talks = talkService.findAllByUserId(getUser().getId());
        for (Talk talk : talks) {
            talk.setSourceUserLogin(userService.findById(talk.getSourceUserId()).getLogin());
            talk.setTargetUserLogin(userService.findById(talk.getTargetUserId()).getLogin());
        }
        view.put("talks", talks);
    }

    private void submit(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String receiverLogin = request.getParameter("select");
        String text = request.getParameter("textarea");
        long targetId;
        try {
            targetId = userService.findByLogin(receiverLogin).getId();
        } catch (NullPointerException e) {
            throw new ValidationException("You should pick a user");
        }

        Talk talk = new Talk();
        talk.setSourceUserId(getUser().getId());
        talk.setTargetUserId(targetId);
        talk.setText(text);
        talkService.validateTalk(talk);

        talkService.addTalk(talk);

        setMessage("Your message was successfully sent to " + receiverLogin);
    }
}
