package org.greenSnake.Times;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter("/time")
public class TimeFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        if (!req.getParameterMap().containsKey("timezone") & req.getCookies() == null) {
            res.addCookie(new Cookie("timezone", "UTC"));
            ;
            chain.doFilter(req, res);
        } else if (!req.getParameterMap().containsKey("timezone")) {
            Cookie[] lastTimeZone = req.getCookies();
            if (Time.isTimeZoneValid(lastTimeZone[0].getValue())) {
                chain.doFilter(req, res);
            } else {
                res.setStatus(400);
                res.getWriter().write("Invalid timezone1");
                res.getWriter().close();
            }
        } else if (Time.isTimeZoneValid(req.getParameter("timezone"))) {
            chain.doFilter(req, res);
        }else {
            res.setStatus(400);
            res.getWriter().write("Invalid timezone");
            res.getWriter().close();
        }
    }
}
