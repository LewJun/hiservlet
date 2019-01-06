package com.microandroid.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 演示页面跳转
 * <pre>
 *     1. redirect
 *     2. forward
 *     3. setHeader
 * </pre>
 *
 * @author huiye
 */
public class RedirectPageServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedirectPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("doPost");
        req.setAttribute("key1", "value1");

        // 1
        String url = "http://bing.com";// 绝对路径（外部）ok
        resp.sendRedirect(url); // 绝对路径 ok
        url = "/WEB-INF/views/rp.jsp";// 页面不能显示 http://localhost:7070/WEB-INF/views/rp.jsp
        url = "/servlet/login"; // 不能跳转 http://localhost:7070/servlet/login
        url = "http://localhost:7070/hiservlet";

        // 2
//        url = "/WEB-INF/views/rp.jsp"; // 可以跳转，并且能得到req中的attribute value 浏览器地址不改变，仍然是 http://localhost:7070/hiservlet/servlet/redirectpage
//        url = "/servlet/login";// 可以跳转，但不会进入过滤器，浏览器地址不改变，仍然是 http://localhost:7070/hiservlet/servlet/redirectpage
//        url = "https://bing.com"; 发生异常，不能跳转到外部链接
//        req.getRequestDispatcher(url).forward(req, resp);

        // 3 302 浏览器重定向
        // url = "/WEB-INF/views/rp.jsp"; http://localhost:7070/WEB-INF/views/rp.jsp
        //url = "servlet/login"; // http://localhost:7070/hiservlet/servlet/servlet/login
        // url = "/servlet/login"; // http://localhost:7070/servlet/login
//        url = "https://bing.com";// 绝对路径 ok 跳转到bing.com
//        url = "http://localhost:7070/hiservlet";// 绝对路径 ok http://localhost:7070/hiservlet/
//        resp.setStatus(resp.SC_MOVED_TEMPORARILY);
//        resp.setHeader("Location", url);
    }
}
