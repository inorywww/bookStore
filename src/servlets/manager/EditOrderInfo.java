


package servlets.manager;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.Book;
import Dao.VO.Order;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/servlets/editOrderInfo")
public class EditOrderInfo extends HttpServlet {
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
        List<Order> orders = new ArrayList<Order>();
        String orderID = req.getParameter("orderID");
        String account = req.getParameter("account");
        String status = req.getParameter("status");
        String buy_num = req.getParameter("buy_num");
        String totalPrice = req.getParameter("totalPrice");
        String booksID = req.getParameter("booksID");
        String booksNum = req.getParameter("booksNum");


        Order order = DaoFactory.getOrderInstance().queryById(Integer.parseInt(orderID)).get(0);

        order.setOrderID(Integer.parseInt(orderID));
        order.setAccount(account);
        order.setStatus(status);
        order.setBuy_num(Integer.parseInt(buy_num));
        order.setTotalPrice(Float.parseFloat(totalPrice));
        order.setBooksID(booksID);
        order.setBooksNum(booksNum);
        DaoFactory.getOrderInstance().update(order);

        orders = DaoFactory.getOrderInstance().queryAllOrder();
//
        resp.getWriter().write(gson.toJson(orders));
    }
}

