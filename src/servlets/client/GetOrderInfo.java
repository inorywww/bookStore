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

@WebServlet("/servlets/getOrderInfo")

public class GetOrderInfo extends HttpServlet {
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

//        String account = (String) req.getSession().getAttribute("account");
        String account = req.getParameter("account");

        Gson gson = new Gson();
        List<Order> orders = DaoFactory.getOrderInstance().queryByAO(account);
        //0: 已完成
        //1：未付款
        //2：已付款
        //3：已发货
        //4：已取消

        List<Order> resOrders = new ArrayList<Order>();
        for (Order o: orders){
            if (o.getAccount().equals(account)){
                resOrders.add(o);
            }
        }
        resp.getWriter().write(gson.toJson(resOrders));
    }

}
