package servlets.client;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/servlets/register")
public class Register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        doPost(req, resp);
        PrintWriter out = resp.getWriter();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        String account = req.getParameter("account");
        String password1 = req.getParameter("password");
        String password2 = req.getParameter("passwordRe");
        System.out.println(account);
        System.out.println(password1);
        System.out.println(password2);

        User user = new User();
        List<User> users = DaoFactory.getUserDaoInstance().getAllUser();
        String res = "";
        Gson gson = new Gson();

        for (User u : users) {
            if (u.getAccount().equals(account)) {
                res = "accountExist";
                resp.getWriter().write(gson.toJson(res));
                return;
            }
        }

        if (!password1.equals(password2)) {
            res = "passwordError";
        } else {
            user.setAccount(account);
            user.setPassword(password1);
            user.setAccName("");
            user.setTel("");
            user.setAddress("");
            req.getSession().setAttribute("account", account);
            boolean flag = DaoFactory.getUserDaoInstance().register(user);
            if (flag) {
                res = "success";
            }
        }
        resp.getWriter().write(gson.toJson(res));

    }
}
