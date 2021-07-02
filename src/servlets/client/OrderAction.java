package servlets.client;

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

@WebServlet("/servlets/orderAction")

public class OrderAction extends HttpServlet {
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
        String account = (String) req.getSession().getAttribute("account");
        String orderID = req.getParameter("orderID");
        String way = req.getParameter("way");
        System.out.println(account);
        System.out.println(orderID);
        System.out.println(way);
        //0: 已完成
        //1：未付款
        //2：已付款
        //3：已发货
        //4：已取消
        if (way.equals("del")) {
            DaoFactory.getOrderInstance().delete(Integer.parseInt(orderID));
        }

        // 返回所有订单
        List<Order> resOrders = new ArrayList<Order>();
        List<Order> orders = DaoFactory.getOrderInstance().queryAllOrder();
        for (Order o : orders) {
            if (o.getAccount().equals(account)) {
                resOrders.add(o);
            }
        }
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(resOrders));
    }
}
