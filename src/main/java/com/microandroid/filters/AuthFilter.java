package com.microandroid.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 认证过滤器
 */
public class AuthFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("doFilter");

        LOGGER.info(" before ");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        if (req.getServletPath().equals("/loginServlet")) {
            filterChain.doFilter(req, resp);
        } else {
            HttpSession session = req.getSession(true);
            Object loginEmp = session.getAttribute("loginEmp");
            if (loginEmp != null) {
                filterChain.doFilter(req, resp);
            } else {
                req.getRequestDispatcher("/").forward(req, resp);
                return;
            }
        }
        LOGGER.info(" after ");
    }

    @Override
    public void destroy() {

    }
}
