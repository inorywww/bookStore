package servlets.manager;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.Order;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/servlets/getAllOrder")

public class GetAllOrder extends HttpServlet {
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
        System.out.println("doPost");

        Gson gson = new Gson();
        List<Order> orders = DaoFactory.getOrderInstance().queryAllOrder();
        //0: 已完成
        //1：未付款
        //2：已付款
        //3：已发货
        //4：已取消

        resp.getWriter().write(gson.toJson(orders));
    }

}
