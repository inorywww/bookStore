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

@WebServlet("/servlets/updatePwd")
public class UpdatePwd extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        doPost(req, resp);
        PrintWriter out = resp.getWriter();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String account = (String) req.getSession().getAttribute("account");
        String oldPassword = req.getParameter("oldPassword");
        String newPassword = req.getParameter("newPassword");
        String newPasswordRe = req.getParameter("newPasswordRe");

        User user = DaoFactory.getUserDaoInstance().getUserInfo(account);
        String res = "";
        Gson gson = new Gson();

        if (oldPassword.equals(newPassword)){
            res = "samePassword";
            resp.getWriter().write(gson.toJson(res));
            return;
        }

        if (!oldPassword.equals(user.getPassword())) {
            res = "OldPasswordError";
            resp.getWriter().write(gson.toJson(res));
            return;
        }
        if (!newPassword.equals(newPasswordRe)) {
            res = "newPasswordError";
        } else {
            user.setPassword(newPassword);
            DaoFactory.getUserDaoInstance().update(user);
            res = "success";
        }
        resp.getWriter().write(gson.toJson(res));

    }
}
