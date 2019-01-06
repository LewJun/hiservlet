package com.microandroid.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

public class SessionServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
//        获取session创建时间
        Date creationTime = new Date(session.getCreationTime());
//        获取该网页最后访问时间
        Date lastAccessedTime = new Date(session.getLastAccessedTime());

        String visitCountKey = "visitCount";
        Integer visitCount = 0;
        if (null == session.getAttribute(visitCountKey)) {
            session.setAttribute(visitCountKey, visitCount);
        }

        String userIdKey = "userId";
        String userId = "Runoob";
        if (session.isNew() || session.getAttribute(userIdKey) == null) {
            userId += "-" + session.getId();
            session.setAttribute(userIdKey, userId);
        } else {
            visitCount = (Integer) session.getAttribute(visitCountKey) + 1;
            session.setAttribute(visitCountKey, visitCount);

            userId = (String) session.getAttribute(userIdKey);
        }

        resp.getWriter().println(
                userId
                        + " " + visitCount
                        + " " + creationTime
                        + " " + lastAccessedTime
        );
    }
}
