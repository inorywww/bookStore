package Dao.DaoImpl;

import Dao.DBConn.DBUtils;
import Dao.DaoFactory.DaoFactory;
import Dao.IDao.BookDao;
import Dao.VO.Book;
import org.apache.commons.lang.StringEscapeUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    @Override
    public void init() {
        List<Book> books = new ArrayList<Book>();
        try {
            books = DaoFactory.getBookDaoInstance().queryAllBook();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        //将所有图书购买数量初始化为1
        connection = DBUtils.getConnection();
        for (Book book : books) {
            String sql = "update bookInfo set buy_num = 1 where id = " + book.getId();
            try {
                statement = connection.prepareStatement(sql);
                statement.execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    //添加操作
    public void insert(Book book) {
        String sql = "insert into bookInfo values(?,?,?,?,?,?,?,?,?,?,?,?,?);";
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, book.getId());
            statement.setString(2, book.getName());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getAmount());
            statement.setInt(5, book.getBuy_num());
            statement.setFloat(6, book.getPrice());
            statement.setString(7, book.getUrl());
            statement.setString(8, book.getCategory());
            statement.setString(9, book.getPDate());
            statement.setString(10, book.getPress());
            statement.setInt(11, book.getPages());
            statement.setString(12, book.getISBN());
            statement.setString(13, book.getIntroduction());
            sql = StringEscapeUtils.escapeSql(sql);
            statement.execute();

            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    //删除操作
    public void delete(int id) {
        String sql = "delete from bookInfo where id = " + id;
        sql = StringEscapeUtils.escapeSql(sql);
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.executeUpdate();
            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    //更改操作
    public void update(Book book) {
        String sql = "update bookInfo set id = ?, name = ?, author = ?, amount = ?, buy_num = ?, price = ?,url = ?, category = ?, pDate = ?, press = ?,pages = ?,ISBN=?,introduction=? where id = " + book.getId();
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, book.getId());
            statement.setString(2, book.getName());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getAmount());
            statement.setInt(5, book.getBuy_num());
            statement.setFloat(6, book.getPrice());
            statement.setString(7, book.getUrl());
            statement.setString(8, book.getCategory());
            statement.setString(9, book.getPDate());
            statement.setString(10, book.getPress());
            statement.setInt(11, book.getPages());
            statement.setString(12, book.getISBN());
            statement.setString(13, book.getIntroduction());

            statement.executeUpdate();

            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    //更改购买数量
    public void updateBuyNum(Book book) {
        String sql = "update bookInfo set buy_num = ? where id = " + book.getId();

        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, book.getBuy_num());
            statement.executeUpdate();

            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    //查询操作
    //书名|作者|描述模糊查询
    public List<Book> queryByNAD(String keyWords) {
        keyWords = StringEscapeUtils.escapeSql(keyWords);
//        keyWords = DealKeywords.dealKeyWords(keyWords);
        String sql = "select * from bookInfo where (name like '%" + keyWords + "%' or author like '%" + keyWords + "%')";
        List<Book> books = new ArrayList<Book>();
        Book book = null;
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book = new Book();
                setBookAttributes(book);
                books.add(book);
            }
            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    @Override
    public List<Book> queryById(int bookId) {
        String sql = "SELECT * from bookInfo where id = " + bookId;
        List<Book> books = new ArrayList<Book>();
        Book book = new Book();
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

    @Override
    public List<Book> queryAllBook() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM bookInfo order by id";
        List<Book> books = new ArrayList<Book>();
        Book book = null;
        connection = DBUtils.getConnection();

        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                book = new Book();
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

    @Override
    public List<Book> queryByCategory(String category) {
        String sql = "SELECT * from bookInfo where category = " + category;
        List<Book> books = new ArrayList<Book>();
        Book book = new Book();
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

    private void setBookAttributes(Book book) throws SQLException {
        book.setId(resultSet.getInt(1));
        book.setName(resultSet.getString(2));
        book.setAuthor(resultSet.getString(3));
        book.setAmount(resultSet.getInt(4));
        book.setBuy_num(resultSet.getInt(5));
        book.setPrice(resultSet.getFloat(6));
        book.setUrl(resultSet.getString(7));
        book.setCategory(resultSet.getString(8));
        book.setPDate(resultSet.getString(9));
        book.setPress(resultSet.getString(10));
        book.setPages(resultSet.getInt(11));
        book.setISBN(resultSet.getString(12));
        book.setIntroduction(resultSet.getString(13));
    }
}
