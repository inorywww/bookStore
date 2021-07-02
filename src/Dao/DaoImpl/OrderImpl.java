package Dao.DaoImpl;

import Dao.DBConn.DBUtils;
import Dao.IDao.OrderDao;
import Dao.VO.Book;
import Dao.VO.Order;
import org.apache.commons.lang.StringEscapeUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderImpl implements OrderDao {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    @Override
    public void init() {

    }

    @Override
    public void insert(Order order) {
        String sql = "insert into orderInfo values(?,?,?,?,?,?,?);";
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, order.getOrderID());
            statement.setString(2, order.getAccount());
            statement.setString(3, order.getStatus());
            statement.setInt(4, order.getBuy_num());
            statement.setFloat(5, order.getTotalPrice());
            statement.setString(6, order.getBooksID());
            statement.setString(7, order.getBooksNum());

            sql = StringEscapeUtils.escapeSql(sql);
            statement.execute();

            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int orderID) {
        String sql = "delete from orderInfo where orderID = " + orderID;
        sql = StringEscapeUtils.escapeSql(sql);
        System.out.println(sql);
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
//            statement.setInt(1, order.getOrderID());
            statement.executeUpdate();

            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Order order) {
        String sql = "update orderInfo set orderID = ?, account = ?, status = ?, buy_num = ?,totalPrice = ?,booksID = ?, booksNum = ? where orderID = "+order.getOrderID();

        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, order.getOrderID());
            statement.setString(2, order.getAccount());
            statement.setString(3, order.getStatus());
            statement.setInt(4, order.getBuy_num());
            statement.setFloat(5, order.getTotalPrice());
            statement.setString(6, order.getBooksID());
            statement.setString(7, order.getBooksNum());

            statement.executeUpdate();
            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    //account|status模糊查询
    public List<Order> queryByAO(String keyWords) {
        keyWords = StringEscapeUtils.escapeSql(keyWords);
        String sql = "select * from orderInfo where (account like '%" + keyWords + "%' or status like '%" + keyWords + "%')";
        List<Order> orders = new ArrayList<Order>();
        Order order = null;
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                order = new Order();
                setOrderAttributes(order);
                orders.add(order);
            }
            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        setStatus(orders);
        return orders;
    }

    @Override
    public List<Order> queryById(int orderId) {
        String sql = "SELECT * from orderInfo where orderID = " + orderId;
        List<Order> orders = new ArrayList<Order>();
        Order order = new Order();
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                setOrderAttributes(order);
                orders.add(order);
            }
            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        setStatus(orders);
        return orders;
    }

    @Override
    public List<Order> queryAllOrder(){
        String sql = "SELECT * FROM orderInfo order by orderID";
        List<Order> orders = new ArrayList<Order>();
        Order order = null;
        connection = DBUtils.getConnection();

        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                order = new Order();
                setOrderAttributes(order);
                orders.add(order);
            }
            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        setStatus(orders);
        return orders;
    }

    private void setOrderAttributes(Order order) throws SQLException {
        order.setOrderID(resultSet.getInt(1));
        order.setAccount(resultSet.getString(2));
        order.setStatus(resultSet.getString(3));
        order.setBuy_num(resultSet.getInt(4));
        order.setTotalPrice(resultSet.getFloat(5));
        order.setBooksID(resultSet.getString(6));
        order.setBooksNum(resultSet.getString(7));
    }
}
