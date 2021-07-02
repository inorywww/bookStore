package servlets.client;

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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@WebServlet("/servlets/submitOrder")

public class SubmitOrder extends HttpServlet {
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
//        String account = (String) req.getSession().getAttribute("account");
       String account = req.getParameter("account");
        String way = req.getParameter("way");
        System.out.println(account);
        //0: 已完成
        //1：未付款
        //2：已付款
        //3：已发货
        //4：已取消
        List<Order> orders = DaoFactory.getOrderInstance().queryAllOrder();
        List<Integer> ids = new ArrayList<Integer>(); //存放所有orderID

        if (way.equals("submitOrder")) {//提交订单
            for (Order o : orders) {
                ids.add(o.getOrderID());
            }
            int newID = Collections.max(ids) + 1;
            Order order = new Order();
            order.setOrderID(newID);
            order.setAccount(account);
            order.setStatus("未付款");
            order.setBuy_num(0);
            order.setTotalPrice(0);
            String booksID = "";
            String booksNum = "";
            List<Book> books = new ArrayList<>();
            books = DaoFactory.getCartDaoInstance().queryAllBook();
            for (Book book : books) {
                order.setBuy_num(order.getBuy_num() + book.getBuy_num());
                order.setTotalPrice(order.getTotalPrice() + book.getBuy_num() * book.getPrice());
                booksID += book.getId() + "-";
                booksNum += book.getBuy_num() + "-";
            }
            booksID = booksID.substring(0, booksID.length() - 1);
            booksNum = booksNum.substring(0, booksNum.length() - 1);
            System.out.println(booksID);
            System.out.println(booksNum);
            order.setBooksID(booksID);
            order.setBooksNum(booksNum);
            DaoFactory.getOrderInstance().insert(order);
        }

        // 提交订单后，清空购物车
        DaoFactory.getCartDaoInstance().init();
        List<Book> resBooks = DaoFactory.getCartDaoInstance().queryAllBook();
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(resBooks));
    }
}
