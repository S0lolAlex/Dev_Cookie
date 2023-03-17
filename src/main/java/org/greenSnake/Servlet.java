package org.greenSnake;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.greenSnake.Times.Time;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.io.IOException;
import java.util.Map;

// Set templates path
@WebServlet("/time")
public class Servlet extends HttpServlet {
    private TemplateEngine engine;

    @Override
    public void init() {
        engine = new TemplateEngine();

        FileTemplateResolver resolver = new FileTemplateResolver();
        resolver.setPrefix("D:/module_9/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML5");
        resolver.setOrder(engine.getTemplateResolvers().size());
        resolver.setCacheable(false);
        engine.addTemplateResolver(resolver);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        Cookie[] lastTimezone = req.getCookies();
        String timezone =
                req.getParameterMap().containsKey("timezone") ? req.getParameter("timezone") : lastTimezone[0].getValue();
        resp.addCookie(new Cookie("timezone", timezone.replace(" ", "+")));
        String time = new Time().getTime(timezone);
        Context simpleContext = new Context(
                resp.getLocale(),
                Map.of("params", time));

        engine.process("time", simpleContext, resp.getWriter());
        resp.getWriter().close();
    }
}