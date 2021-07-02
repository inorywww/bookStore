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

@WebServlet("/servlets/getCount")
public class GetCount extends HttpServlet {
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
        List<User> users = DaoFactory.getUserDaoInstance().getAllUser();
        List<Book> books = new ArrayList<>();
        try {
            books = DaoFactory.getBookDaoInstance().queryAllBook();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        List<Order> orders = DaoFactory.getOrderInstance().queryAllOrder();
        ResCount res = new ResCount(users.size(), books.size(), orders.size());
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(res));
    }

    class ResCount {
        int userCount;
        int bookCount;
        int orderCount;

        public ResCount(int userCount, int bookCount, int orderCount) {
            this.userCount = userCount;
            this.bookCount = bookCount;
            this.orderCount = orderCount;
        }
    }
}
