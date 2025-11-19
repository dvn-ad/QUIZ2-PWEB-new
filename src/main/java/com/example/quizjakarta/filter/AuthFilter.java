package com.example.quizjakarta.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String loginURI = req.getContextPath() + "/login";
        String cssURI = req.getContextPath() + "/css/style.css";
        String requestURI = req.getRequestURI();

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = requestURI.equals(loginURI);
        boolean cssRequest = requestURI.startsWith(req.getContextPath() + "/css/"); // Allow all CSS

        if (loggedIn || loginRequest || cssRequest) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(loginURI);
        }
    }
}
