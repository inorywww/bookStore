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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/servlets/addUser")

public class AddUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        System.out.println("doGet");

        String account = req.getParameter("account");
        String password = req.getParameter("password");
        String accName = req.getParameter("accName");
        String tel = req.getParameter("tel");
        String address = req.getParameter("address");

        Gson gson = new Gson();
        List<User> users =DaoFactory.getUserDaoInstance().getAllUser();
        for (User u:users){
            if (u.getAccount().equals(account)){
                resp.getWriter().write("账户已存在");
                return;
            }
        }

        User user = new User();
        user.setAccount(account);
        user.setAccName(accName);
        user.setPassword(password);
        user.setTel(tel);
        user.setAddress(address);

        DaoFactory.getUserDaoInstance().insert(user);
        users = DaoFactory.getUserDaoInstance().getAllUser();

        resp.getWriter().write(gson.toJson(users));
    }

}
