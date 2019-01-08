package com.microandroid.servlet;

import com.microandroid.bean.Emp;
import com.microandroid.utils.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 登陆控制器
 *
 * @author huiye
 */
public class LoginServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    public void init() throws ServletException {
        LOGGER.info("init");
        super.init();
    }

    @Override
    public void destroy() {
        LOGGER.info("destroy");
        super.destroy();
    }

    @Override
    public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("service");
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOGGER.info("doGet");
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LOGGER.info("doPost");
        req.setCharacterEncoding("utf-8");

        HttpSession session = req.getSession(true);

        // 得到cookies
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            LOGGER.info("cookie->name:{}," +
                            "value:{}," +
                            "path:{}," +
                            "maxAge:{}," +
                            "domain:{}," +
                            "comment:{}," +
                            "version:{}",
                    cookie.getName(),
                    cookie.getValue(),
                    cookie.getPath(),
                    cookie.getMaxAge(),
                    cookie.getDomain(),
                    cookie.getComment(),
                    cookie.getVersion()
            );
        }

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String sql = "select empno, ename, job from emp where ename=?";
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);// 从1开始
            ResultSet rs = preparedStatement.executeQuery();
            Emp emp = null;
            while (rs != null && rs.next()) {
//                rs.getInt(1); 从1开始
                int empno = rs.getInt("empno");
                String ename = rs.getString("ename");
                String job = rs.getString("job");

                emp = new Emp(empno, ename, job);

                LOGGER.info("emp={}", emp);
            }

            if (emp == null) {
                throw new RuntimeException("404 Not Found");
            }

            session.setAttribute("loginEmp", emp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DbUtil.close(conn);
        }

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");

//        try {
//            // 模拟发生异常
//            int i = 1 / 0;
//        } catch (Exception e) {
//            // 模拟发生异常，把异常抛出去
//            throw new RuntimeException(e);
//        }
        // 设置响应码 可能会出现问题 Cannot forward after response has been committed
//        resp.sendError(200, "ok");

        Cookie cookie = new Cookie("ename", username);
//        当设置时间，关闭浏览器后，cookie可能还会存在
//        未设置时间，关闭浏览器后，cookie将会跟随session一起清除
//        setMaxAge(0)可以删除cookie
//        cookie.setMaxAge(1 * 60*60);// 60min
//        设置 cookie 适用的路径。
//        如果不指定路径，与当前页面相同目录下的（包括子目录下的）所有 URL 都会返回 cookie。
        cookie.setPath("/");
//        好像设置了无用
        cookie.setComment("ename");
//        使用response 添加cookie
        resp.addCookie(cookie);

//        设置 Refresh 头信息，从而实现自动刷新页面。
//        resp.setIntHeader("Refresh", 5);
        // 跳转到welcome页面
        req.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(req, resp);
    }
}
