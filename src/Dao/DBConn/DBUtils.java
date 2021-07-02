package Dao.DBConn;

import java.sql.*;
import java.util.ResourceBundle;


public class DBUtils {
    public static String Driver;
    public static String URL;
    public static String USERNAME;
    public static String PASSWORD;

    private static final ResourceBundle resource = ResourceBundle.getBundle("jdbc");

    //静态代码块在加载类时只会被执行一次
    static {
        Driver = resource.getString("jdbc.Driver");
        URL = resource.getString("jdbc.URL");
        USERNAME = resource.getString("jdbc.USERNAME");
        PASSWORD = resource.getString("jdbc.PASSWORD");

        try {
            Class.forName(Driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //开启数据库连接
    public static Connection getConnection() {
        try {
            Class.forName(Driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接数据库失败!");
        }
        return conn;
    }

    //关闭数据库连接
    public static void close(ResultSet rs, Statement stat, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
