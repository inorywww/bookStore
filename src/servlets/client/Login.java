package servlets.client;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/servlets/login")

public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        System.out.println("doGet");
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        System.out.println("doPost");

        //获取前端返回的数据
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        User user = new User();
        user.setAccount(account);
        user.setPassword(password);
        User resUser = DaoFactory.getUserDaoInstance().getUserInfo(account);
        if(account.equals(resUser.getAccount()) && password.equals(resUser.getPassword()))
        {
            req.getSession().setAttribute("account",account);
            resp.getWriter().write("success");
        }
        else {
            resp.getWriter().write("error");
        }
    }
}
