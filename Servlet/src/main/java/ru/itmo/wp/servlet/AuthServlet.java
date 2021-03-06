package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class AuthServlet extends HttpServlet {
    private final ArrayList<Message> messages = new ArrayList<>();

    private void makeResponse(HttpServletResponse response, Object object) throws IOException {
        String json = new Gson().toJson(object);
        response.getWriter().print(json);
        response.getWriter().flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession(true);
        response.setContentType("application/json");
        switch (uri) {
            case "/message/auth" :
                String name = (String) session.getAttribute("user");
                if (name == null) {
                    name = request.getParameter("user");
                    if (name == null) {
                        name = "";
                    } else {
                        session.setAttribute("user", name);
                    }
                }
                makeResponse(response, name);
                break;
            case "/message/findAll":
                synchronized (messages) {
                    makeResponse(response, messages);
                    break;
                }
            case "/message/add":
                if (session.getAttribute("user") != null) {
                    synchronized (messages) {
                        //TODO: fixed
                        String user = (String) session.getAttribute("user");
                        String text = request.getParameter("text");
                        messages.add(new Message(user, text));
                        break;
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
        }
    }

    private static class Message {
        private final String user;
        private final String text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }

}
