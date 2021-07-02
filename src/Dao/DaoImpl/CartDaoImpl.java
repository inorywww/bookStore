package Dao.DaoImpl;

import Dao.DBConn.DBUtils;
import Dao.IDao.CartDao;
import Dao.VO.Book;
import org.apache.commons.lang.StringEscapeUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import Model.DealKeywords;

public class CartDaoImpl implements CartDao {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    @Override
    public void init() {
        connection = DBUtils.getConnection();
        //清空购物车
        String sql = "truncate table cartInfo";
        try {
            statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    @Override
    //添加操作
    public void insert(Book book) {
        String sql = "insert into cartInfo values(?,?,?,?,?,?);";
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, book.getId());
            statement.setString(2, book.getName());
            statement.setString(3, book.getAuthor());
            statement.setInt(4, book.getBuy_num());
            statement.setFloat(5, book.getPrice());
            statement.setString(6, book.getUrl());

            sql = StringEscapeUtils.escapeSql(sql);
            statement.execute();

            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    //删除某一列
    public void delete(int id){
        String sql = "delete from cartInfo where id = "+id;
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
    //更改购买数量
    public void updateBuyNum(Book book) {
        String sql = "update cartInfo set buy_num = ? where id = " + book.getId();
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
        String sql = "select * from cartInfo where (name like '%" + keyWords + "%' or author like '%"
                + keyWords + "%' or describe like '%" + keyWords + "%')";
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
            System.out.println("error");
        }

        return books;
    }

    @Override
    public List<Book> queryById(int bookId) {
        String sql = "SELECT * from cartInfo where id = " + bookId;
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
    public List<Book> queryAllBook(){
        String sql = "SELECT * FROM cartInfo";
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

    private void setBookAttributes(Book book) throws SQLException {
        book.setId(resultSet.getInt(1));
        book.setName(resultSet.getString(2));
        book.setAuthor(resultSet.getString(3));
        book.setBuy_num(resultSet.getInt(4));
        book.setPrice(resultSet.getFloat(5));
        book.setUrl(resultSet.getString(6));
    }

}
