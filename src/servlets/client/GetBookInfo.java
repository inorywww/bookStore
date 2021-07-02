


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
import java.sql.SQLException;
import java.util.*;

@WebServlet("/servlets/getBookInfo")
public class GetBookInfo extends HttpServlet {
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
        try {
            DaoFactory.getBookDaoInstance().init();
//            DaoFactory.getCartDaoInstance().init();
            books = DaoFactory.getBookDaoInstance().queryAllBook();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        //存放所有类型
        List<String> categories = new ArrayList<>();
        for (Book b : books) {
            if (!categories.contains(b.getCategory())) {
                categories.add(b.getCategory());
            }
        }
        //分类之后的结果
         ArrayList<ResBook> res = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            res.add(new ResBook());
        }

        for (Book b : books) {
            for(int i = 0;i<categories.size();i++){
                if (categories.get(i).equals(b.getCategory())){
                    res.get(i).setType(b.getCategory());
                    res.get(i).addContent(b);
                    break;
                }
            }
        }
        resp.getWriter().write(gson.toJson(res));
    }
}

class ResBook {
    private String type = "";
    private ArrayList<Book> contents = new ArrayList<>();

    public ResBook() {

    }

    public ResBook(String type, ArrayList<Book> contents) {
        this.type = type;
        this.contents = contents;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<Book> getContents() {
        return this.contents;
    }

    public void setContents(ArrayList<Book> contents) {
        this.contents = contents;
    }

    public void addContent(Book content) {
        this.contents.add(content);
    }

    public void put(){
        for (Book b:contents){
            System.out.println(b.getId()+"  "+b.getName());
        }
    }
}
