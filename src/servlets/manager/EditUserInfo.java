package servlets.manager;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.Book;
import Dao.VO.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/servlets/editUserInfo")
public class EditUserInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        Gson gson = new Gson();
        List<User> users = new ArrayList<User>();
        String account = req.getParameter("account");
        String password = req.getParameter("password");
        String accName = req.getParameter("accName");
        String tel = req.getParameter("tel");
        String address = req.getParameter("address");

        User user = DaoFactory.getUserDaoInstance().getUserInfo(account);
        user.setAccount(account);
        user.setPassword(password);
        user.setAccName(accName);
        user.setTel(tel);
        user.setAddress(address);
        DaoFactory.getUserDaoInstance().update(user);

        users = DaoFactory.getUserDaoInstance().getAllUser();
        resp.getWriter().write(gson.toJson(users));
    }
}

