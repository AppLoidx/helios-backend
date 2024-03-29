package com.apploidxxx.heliosbackend.handlers;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Arthur Kupriyanov
 */
@Component
public class AuthorizationFilter implements Filter {
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        String redirectUri = req.getContextPath() + "/external/login.html";
        if (req.getRequestURI().matches("/external/.+")
                || req.getRequestURI().equals("/api/auth")
                || req.getRequestURI().matches("/api/oauth.*")
                || req.getRequestURI().equals("/api/register")
                || req.getRequestURI().equals("/error")
                || req.getRequestURI().equals("/")
                || req.getRequestURI().equals("/robots.txt")
                || req.getRequestURI().equals("")
                || req.getRequestURI().matches("/meta/.*")
                || req.getRequestURI().equals("/index.html")
                || req.getRequestURI().equals("/favicon.ico")
                || req.getRequestURI().matches("/assets/.*")
                || req.getRequestURI().matches("/policy.*")
                || req.getRequestURI().matches("/google.*")
                || req.getRequestURI().matches("/api/google/oauth.*")
        ) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = req.getSession();

        if (session == null) {
            res.sendRedirect(redirectUri);
            return;
        } else {
            if (req.getCookies() != null)
                for (Cookie c : req.getCookies()) {
                    if (c.getName().equals("session")) {
                        if (c.getValue() != null) {
                            break;
                        } else {
                            res.sendRedirect(redirectUri);
                            return;
                        }
                    }
                }
        }

        filterChain.doFilter(servletRequest, servletResponse);


    }

    @Override
    public void destroy() {

    }
}
