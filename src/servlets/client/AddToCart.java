package servlets.client;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.Book;
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
import java.util.*;

@WebServlet("/servlets/addToCart")
public class AddToCart extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doPost(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        System.out.println("doPost");


        String id = req.getParameter("id");
        List<Book> books = DaoFactory.getBookDaoInstance().queryById(Integer.parseInt(id));
        Book book = books.get(0);

        System.out.println(book.getName());
        System.out.println(book.getId());

        //获取购物车已有内容
        List<Book> existBooks = new ArrayList<>();

        existBooks = DaoFactory.getCartDaoInstance().queryAllBook();

        // 判断当前购物车是否存在这本书，存在数量+1
        boolean flag = false;
        for (Book bo : existBooks) {
            if (bo.getId() == book.getId()) {
                book.setBuy_num(book.getBuy_num() + bo.getBuy_num());
                DaoFactory.getCartDaoInstance().updateBuyNum(book);
                flag = true;
                break;
            }
        }
        //若不存在当前这本书的话就添加进数据库
        if (!flag) {
            DaoFactory.getCartDaoInstance().insert(book);
        }

        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson("success"));

    }
}
