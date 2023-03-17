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
        if (!req.getParameterMap().containsKey("timezone") & Time.getTime_zone() == null) {
            Time.setTime_zone("UTC");
            res.addCookie(new Cookie("timezone", Time.getTime_zone()));;
            chain.doFilter(req, res);
        }else if(!req.getParameterMap().containsKey("timezone") ){
            Cookie[] cookies = req.getCookies();
            Time.setTime_zone(cookies[0].getValue());
            chain.doFilter(req,res);
        }else if(Time.getTime_zone() != null){
            chain.doFilter(req,res);
        }else {
            res.setStatus(400);
            res.getWriter().write("Invalid timezone");
            res.getWriter().close();
        }
    }
}
