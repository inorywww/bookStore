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

@WebServlet("/servlets/getOrderCategory")
public class GetOrderCategory extends HttpServlet {
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
        DaoFactory.getOrderInstance().init();
        orders = DaoFactory.getOrderInstance().queryAllOrder();
        //存放所有类型
        List<String> StatusCategories = new ArrayList<>();
        for (Order o : orders) {
            if (!StatusCategories.contains(o.getStatus())) {
                StatusCategories.add(o.getStatus());
            }
        }

        //分类之后的结果
        ArrayList<ResOrder> res = new ArrayList<>();
        for (int i = 0; i < StatusCategories.size(); i++) {
            res.add(new ResOrder());
        }

        for (Order o : orders) {
            for (int i = 0; i < StatusCategories.size(); i++) {
                if (StatusCategories.get(i).equals(o.getStatus())) {
                    res.get(i).setType(o.getStatus());
                    res.get(i).addContent(o);
                    break;
                }
            }
        }
        for (ResOrder r:res){
            r.put();
        }
        resp.getWriter().write(gson.toJson(res));
    }
}

class ResOrder {
    private String type = "";
    private ArrayList<Order> contents = new ArrayList<>();

    public ResOrder() {

    }

    public ResOrder(String type, ArrayList<Order> contents) {
        this.type = type;
        this.contents = contents;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Order> getContents() {
        return this.contents;
    }

    public void setContents(ArrayList<Order> contents) {
        this.contents = contents;
    }

    public void addContent(Order content) {
        this.contents.add(content);
    }

    public void put() {
        for (Order b : contents) {
            System.out.println(b.getOrderID() + "  " + b.getStatus());
        }
    }
}
