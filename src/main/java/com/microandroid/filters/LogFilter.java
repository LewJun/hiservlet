package com.microandroid.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

/**
 * 日志过滤器，控制器执行前后处理
 *
 * @author huiye
 */
public class LogFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("应用启动了");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOGGER.info("doFilter");
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        Map<String, String[]> parameterMap = req.getParameterMap();
        for (Map.Entry<String, String[]> me : parameterMap.entrySet()) {
            LOGGER.info("req attr {}:{}", me.getKey(), me.getValue());
        }

        LOGGER.info("ContextPath:{}",req.getContextPath());
        LOGGER.info("Method:{}",req.getMethod());
        LOGGER.info("PathInfo:{}",req.getPathInfo());
        LOGGER.info("PathTranslated:{}",req.getPathTranslated());
        LOGGER.info("ServerName:{}",req.getServerName());
        LOGGER.info("ServletPath:{}",req.getServletPath());
        LOGGER.info("ServerPort:{}",req.getServerPort());
        LOGGER.info("RemoteAddr:{}",req.getRemoteAddr());
        LOGGER.info("RemoteHost:{}",req.getRemoteHost());
        LOGGER.info("RemotePort:{}",req.getRemotePort());
        LOGGER.info("Scheme:{}",req.getScheme());
        LOGGER.info("RequestURI:{}",req.getRequestURI());
        LOGGER.info("QueryString:{}",req.getQueryString());
        LOGGER.info("ContentType:{}",req.getContentType());
        LOGGER.info("ContentLength:{}",req.getContentLength());

        Enumeration headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            LOGGER.info("header {}:{}", headerName, req.getHeader(headerName));
        }


        String requestedSessionId = req.getRequestedSessionId();
        LOGGER.info("sessionId={}", requestedSessionId);

        HttpSession httpSession = req.getSession();
        Enumeration sessionAttributeNames = httpSession.getAttributeNames();
        while (sessionAttributeNames.hasMoreElements()) {
            String key = (String) sessionAttributeNames.nextElement();
            LOGGER.info("session {}:{}", key, httpSession.getAttribute(key));
        }

        LOGGER.info(" before ");
        filterChain.doFilter(req, resp);
        LOGGER.info(" after ");
    }

    @Override
    public void destroy() {
        LOGGER.info("destroy");
    }
}
