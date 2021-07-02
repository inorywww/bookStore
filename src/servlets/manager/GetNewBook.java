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

@WebServlet("/servlets/getNewBook")
public class GetNewBook extends HttpServlet {
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
        List<Book> books = new ArrayList<>();
        try {
            books = DaoFactory.getBookDaoInstance().queryAllBook();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        List<Book> resBooks = new ArrayList<>();
        if (books.size()>=5){
            resBooks.add(books.get(books.size()-1));
            resBooks.add(books.get(books.size()-2));
            resBooks.add(books.get(books.size()-3));
            resBooks.add(books.get(books.size()-4));
            resBooks.add(books.get(books.size()-5));
        }
        else {
            resBooks = books;
        }

        Gson gson = new Gson();
        resp.getWriter().write(gson.toJson(resBooks));
    }

}
