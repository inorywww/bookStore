package servlets.manager;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.User;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/servlets/searchUser")
public class SearchUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        System.out.println("doPost");

        //获取前端返回的数据
        String searchContent = req.getParameter("searchContent");
        List<User> users = DaoFactory.getUserDaoInstance().queryByAN(searchContent);
        for (User u:users){
            System.out.println(u.getAccount());
            System.out.println(u.getAccName());
        }
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(users));
    }
}
