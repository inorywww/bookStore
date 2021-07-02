package servlets.client;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.*;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet("/servlets/getOrderDetail")

public class GetOrderDetail extends HttpServlet {
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
        String orderID = req.getParameter("orderID");
        System.out.println("orderID:" + orderID);
        Gson gson = new Gson();

        //订单信息
        Order order = DaoFactory.getOrderInstance().queryById(Integer.parseInt(orderID)).get(0);
        //用户信息
        User user = DaoFactory.getUserDaoInstance().getUserInfo(order.getAccount());
        //书籍信息
        List<Book> books = new ArrayList<>();

        //购买的书id
        String[] ids = order.getBooksID().split("-");
        //每本书购买的数量
        String[] nums = order.getBooksNum().split("-");
        int buy_num = 0;
        float totalPrice = 0;
        for (int i = 0; i < ids.length;i++) {
            Book book = DaoFactory.getBookDaoInstance().queryById(Integer.parseInt(ids[i])).get(0);
            book.setBuy_num(Integer.parseInt(nums[i]));
            buy_num += Integer.parseInt(nums[i]);
            totalPrice += Integer.parseInt(nums[i]) * book.getPrice();
            books.add(book);
        }

        order.setBuy_num(buy_num);
        order.setTotalPrice(totalPrice);
        ResData resData  = new ResData(order,user,books);
        //0: 已完成
        //1：未付款
        //2：已付款
        //3：已发货
        //4：已取消


        resp.getWriter().write(gson.toJson(resData));
    }

    private static class ResData{
        Order order = new Order();
        User user = new User();
        List<Book> books = new ArrayList<>();//存放书籍信息

        public ResData(Order order, User user, List<Book> books) {
            this.order = order;
            this.user = user;
            this.books = books;
        }
    }
}