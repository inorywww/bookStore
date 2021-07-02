package servlets.client;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.Book;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/servlets/changeCartNum")

public class ChangeCartNum extends HttpServlet {
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
        String way = req.getParameter("way");
        String id = req.getParameter("id");
        List<Book> books = new ArrayList<Book>();
        Book book = DaoFactory.getCartDaoInstance().queryById(Integer.parseInt(id)).get(0);
        //减少
        if (way.equals("-")) {
            System.out.println("减少");
            book.setBuy_num(book.getBuy_num() - 1);
        }
        //增加
        else {
            System.out.println("增加");
            book.setBuy_num(book.getBuy_num() + 1);
        }
        DaoFactory.getCartDaoInstance().updateBuyNum(book);
        books = DaoFactory.getCartDaoInstance().queryAllBook();
        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(books));

    }
}
