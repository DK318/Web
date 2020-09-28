package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (request.getMethod().equals("GET") && session.getAttribute("captcha") == null
                && !request.getRequestURI().equals("/favicon.ico")) {
            String answer = request.getParameter("answer");
            String number = (String) session.getAttribute("number");
            if (number != null && number.equals(answer)) {
                session.setAttribute("captcha", "ok");
                response.sendRedirect(request.getRequestURI());
            } else {
                String rngNumber = Integer.toString(new Random().nextInt(900) + 100);
                String captchaImg = Base64.getEncoder().encodeToString(ImageUtils.toPng(rngNumber));
                session.setAttribute("number", rngNumber);
                session.setAttribute("captcha-img", captchaImg);
                response.setContentType("text/html");
                request.getRequestDispatcher("/static/jsp/captcha.jsp").forward(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

}
