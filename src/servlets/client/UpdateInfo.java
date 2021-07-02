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

@WebServlet("/servlets/updateInfo")
public class UpdateInfo extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
        doPost(req, resp);
        PrintWriter out = resp.getWriter();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        String account =  req.getParameter("account");
        String accName =  req.getParameter("accName");
        String tel = req.getParameter("tel");
        String address = req.getParameter("address");
        User user = DaoFactory.getUserDaoInstance().getUserInfo(account);

        System.out.println(accName);
        System.out.println(tel);
        System.out.println(address);
        if (!accName.equals("")){
            user.setAccName(accName);
        }
        if (!tel.equals("")){
            user.setTel(tel);
        }
        if (!address.equals("")){
            user.setAddress(address);
        }

        DaoFactory.getUserDaoInstance().update(user);
        User resUser = DaoFactory.getUserDaoInstance().getUserInfo(account);
        Gson gson = new Gson();

        resp.getWriter().write(gson.toJson(resUser));

    }
}
