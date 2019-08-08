package com.apploidxxx.heliosbackend.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    Logger logger = LoggerFactory.getLogger(AuthorizationFilter.class);
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
                || req.getRequestURI().equals("/api/register")
                || req.getRequestURI().equals("/error")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        HttpSession session = req.getSession(false);

        if (session == null) {
            res.sendRedirect(redirectUri);
            return;
        } else {
            for (Cookie c : req.getCookies()) {
                if (c.getName().equals("session")) {
                    if (c.getValue() != null) break;
                    else {
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
