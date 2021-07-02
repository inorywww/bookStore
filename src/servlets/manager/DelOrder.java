package servlets.manager;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.Book;
import Dao.VO.Order;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/servlets/delOrder")
public class DelOrder extends HttpServlet {
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

        //获取前端返回的数据
        String orderID = req.getParameter("orderID");
        System.out.println(orderID);
        DaoFactory.getOrderInstance().delete(Integer.parseInt(orderID));
        List<Order> orders = DaoFactory.getOrderInstance().queryAllOrder();
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(orders));
    }
}
