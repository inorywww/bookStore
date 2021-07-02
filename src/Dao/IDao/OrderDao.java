package Dao.IDao;

import Dao.VO.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    public void init();
    //添加操作
    public void insert(Order order);
    //删除操作
    public void delete(int orderID);
    //更改操作
    public void update(Order order);
    //查询操作
    //account|orderStatus模糊查询
    public List<Order> queryByAO(String keyWords);
    //订单ID查询
    public List<Order>  queryById(int orderId);
    //查询全部订单
    public List<Order> queryAllOrder();
}
