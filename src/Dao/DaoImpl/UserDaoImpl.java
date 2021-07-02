package Dao.DaoImpl;


import Dao.DBConn.DBUtils;
import Dao.IDao.UserDao;
import Dao.VO.Book;
import Dao.VO.User;
import jdk.swing.interop.SwingInterOpUtils;
import org.apache.commons.lang.StringEscapeUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDaoImpl implements UserDao {
    Connection connection = null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;

    @Override
    public boolean register(User user) {
        String sql = "INSERT into userInfo values(?,?,?,?,?)";

        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getAccount());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getAccName());
            statement.setString(4, user.getTel());
            statement.setString(5, user.getAddress());
            sql = StringEscapeUtils.escapeSql(sql);
            statement.execute();
            DBUtils.close(resultSet, statement, connection);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public List<User> getAllUser() {
        String sql = "SELECT * from userInfo";
        connection = DBUtils.getConnection();
        List<User> users = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setAccount(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setAccName(resultSet.getString(3));
                user.setTel(resultSet.getString(4));
                user.setAddress(resultSet.getString(5));
                users.add(user);
            }
            DBUtils.close(resultSet, statement, connection);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
        return users;
    }

    @Override
    public User getUserInfo(String account) {
//        String account = user.getAccount();
        account = "'" + account + "'";
        String sql = "SELECT * from userInfo where account = " + account;

        connection = DBUtils.getConnection();
        User user = new User();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user.setAccount(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setAccName(resultSet.getString(3));
                user.setTel(resultSet.getString(4));
                user.setAddress(resultSet.getString(5));
            }
            DBUtils.close(resultSet, statement, connection);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }
        return user;
    }

    @Override
    public void update(User user) {
        String account = "'" + user.getAccount() + "'";
        String sql = "update userInfo set account = ?, password = ?, accName = ?, tel = ?, address = ? where account = " + account;
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getAccount());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getAccName());
            statement.setString(4, user.getTel());
            statement.setString(5, user.getAddress());

            statement.executeUpdate();

            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insert(User user) {
        String sql = "insert into userInfo values(?,?,?,?,?);";
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getAccount());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getAccName());
            statement.setString(4, user.getTel());
            statement.setString(5, user.getAddress());

            sql = StringEscapeUtils.escapeSql(sql);
            statement.execute();

            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String account) {
        String acc = "'" + account + "'";
        String sql = "delete from userInfo where account = " + acc;
//        sql = StringEscapeUtils.escapeSql(sql);
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
    //查询操作
    //书名|作者|描述模糊查询
    public List<User> queryByAN(String keyWords) {
        keyWords = StringEscapeUtils.escapeSql(keyWords);
        String sql = "select * from userInfo where (account like '%" + keyWords + "%' or accName like '%" + keyWords + "%')";
        List<User> users = new ArrayList<User>();
        User user = null;
        connection = DBUtils.getConnection();
        try {
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setAccount(resultSet.getString(1));
                user.setPassword(resultSet.getString(2));
                user.setAccName(resultSet.getString(3));
                user.setTel(resultSet.getString(4));
                user.setAddress(resultSet.getString(5));
                users.add(user);
            }
            DBUtils.close(resultSet, statement, connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
