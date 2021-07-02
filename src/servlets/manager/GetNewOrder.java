package servlets.manager;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.Book;
import Dao.VO.Order;
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
import java.util.List;

@WebServlet("/servlets/getNewOrder")
public class GetNewOrder extends HttpServlet {
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
        List<Order> orders = DaoFactory.getOrderInstance().queryAllOrder();
        List<Order> resOrders = new ArrayList<>();
        if (orders.size()>=5){
            resOrders.add(orders.get(orders.size()-1));
            resOrders.add(orders.get(orders.size()-2));
            resOrders.add(orders.get(orders.size()-3));
            resOrders.add(orders.get(orders.size()-4));
            resOrders.add(orders.get(orders.size()-5));
        }
        else {
            resOrders = orders;
        }
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(resOrders));
    }

}
