


package servlets.manager;

import Dao.DaoFactory.DaoFactory;
import Dao.VO.Book;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/servlets/editBookInfo")
public class EditBookInfo extends HttpServlet {
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
        List<Book> books = new ArrayList<Book>();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String author = req.getParameter("author");
        String amount = req.getParameter("amount");
        String price = req.getParameter("price");
        String category = req.getParameter("category");
        String pDate = req.getParameter("pDate");
        String press = req.getParameter("press");
        String pages = req.getParameter("pages");
        String ISBN = req.getParameter("ISBN");
        String introduction = req.getParameter("introduction");


        System.out.println(id);
        System.out.println(name);
        System.out.println(author);
        System.out.println(amount);
        System.out.println(price);
        System.out.println(category);
        System.out.println(press);

        Book book = DaoFactory.getBookDaoInstance().queryById(Integer.parseInt(id)).get(0);

        book.setName(name);
        book.setAuthor(author);
        book.setAmount(Integer.parseInt(amount));
        book.setPrice(Float.parseFloat(price));
        book.setCategory(category);
        book.setPDate(pDate);
        book.setPress(press);
        book.setPages(Integer.parseInt(pages));
        book.setISBN(ISBN);
        book.setIntroduction(introduction);

        DaoFactory.getBookDaoInstance().update(book);

        try {
            books = DaoFactory.getBookDaoInstance().queryAllBook();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
//
        resp.getWriter().write(gson.toJson(books));
    }
}

