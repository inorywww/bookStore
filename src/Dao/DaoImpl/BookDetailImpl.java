package Dao.DaoImpl;

import Dao.DBConn.DBUtils;
import Dao.IDao.BookDetailDao;
import Dao.VO.Book;
import Dao.VO.BookDetail;
import org.apache.commons.lang.StringEscapeUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDetailImpl implements BookDetailDao {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    //添加操作
    public void insert(BookDetail book) {
        String sql = "insert into bookDetail values(?, ?, ?, ?, ?, ?, ?, ?,?,?,?);";
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, book.getId());
            statement.setString(2, book.getName());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getPress());
            statement.setString(5, book.getpDate());
            statement.setInt(6, book.getPages());
            statement.setFloat(7, book.getPrice());
            statement.setString(8, book.getISBN());
            statement.setString(9, book.getCategory());
            statement.setString(10, book.getIntroduction());
            statement.setString(11, book.getUrl());

            sql = StringEscapeUtils.escapeSql(sql);
            statement.execute();

            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除操作
    public void delete(int id) {
        String sql = "delete * from bookDetail where id = ?;";
        sql = StringEscapeUtils.escapeSql(sql);
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //更改操作
    public void update(BookDetail book) {
        String sql = "update bookDetail set id = ? name = ?, author = ?, press = ?, pDate = ?, pages = ?, price = ?, ISBN = ?,category = ?,introduction = ?,url=? where id = " + book.getId();

        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, book.getId());
            statement.setString(2, book.getName());
            statement.setString(3, book.getAuthor());
            statement.setString(4, book.getPress());
            statement.setString(5, book.getpDate());
            statement.setInt(6, book.getPages());
            statement.setFloat(7, book.getPrice());
            statement.setString(8, book.getISBN());
            statement.setString(9, book.getCategory());
            statement.setString(10, book.getIntroduction());
            statement.setString(11, book.getUrl());

            statement.executeUpdate();

            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询操作
    //书ID查询
    public List<BookDetail> queryById(int bookId) {
        String sql = "SELECT * from bookDetail where id = " + bookId;
        List<BookDetail> books = new ArrayList<BookDetail>();
        BookDetail book = new BookDetail();
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                setBookAttributes(book);
                books.add(book);
            }
            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    //所有书查询
    public List<BookDetail> queryAllBook() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM bookDetail";
        List<BookDetail> books = new ArrayList<BookDetail>();
        BookDetail book = null;
        connection = DBUtils.getConnection();

        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book = new BookDetail();
                setBookAttributes(book);
                books.add(book);
            }
            DBUtils.close(resultSet, statement, connection);
//            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    private void setBookAttributes(BookDetail book) throws SQLException {
        book.setId(resultSet.getInt(1));
        book.setName(resultSet.getString(2));
        book.setAuthor(resultSet.getString(3));
        book.setPress(resultSet.getString(4));
        book.setpDate(resultSet.getString(5));
        book.setPages(resultSet.getInt(6));
        book.setPrice(resultSet.getFloat(7));
        book.setISBN(resultSet.getString(8));
        book.setCategory(resultSet.getString(9));
        book.setIntroduction(resultSet.getString(10));
        book.setUrl(resultSet.getString(11));

    }
}
