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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/servlets/addOrder")

public class AddOrder extends HttpServlet {
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
        Gson gson = new Gson();

        String account = req.getParameter("account");
        String status = req.getParameter("status");
        String booksID = req.getParameter("booksID");
        String booksNum = req.getParameter("booksNum");

        //购买的书id
        String[] bids = booksID.split("-");
        //每本书购买的数量
        String[] nums = booksNum.split("-");
        int buy_num = 0;
        float totalPrice = 0;
        for (int i = 0; i < bids.length;i++) {
            Book book = DaoFactory.getBookDaoInstance().queryById(Integer.parseInt(bids[i])).get(0);
            buy_num += Integer.parseInt(nums[i]);
            totalPrice += Integer.parseInt(nums[i]) * book.getPrice();
        }

        List<Order> orders = new ArrayList<Order>();
        orders = DaoFactory.getOrderInstance().queryAllOrder();
        List<Integer> oids = new ArrayList<Integer>(); //存放所有order ID
        for (Order o : orders) {
            oids.add(o.getOrderID());
        }

        int newID = Collections.max(oids) + 1; //设置新id
        Order order = new Order();
        order.setOrderID(newID);
        order.setAccount(account);
        order.setStatus(status);
        order.setBuy_num(buy_num);
        order.setTotalPrice(totalPrice);
        order.setBooksID(booksID);
        order.setBooksNum(booksNum);

        DaoFactory.getOrderInstance().insert(order);
        orders = DaoFactory.getOrderInstance().queryAllOrder();
        resp.getWriter().write(gson.toJson(orders));
    }

}
