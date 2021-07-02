package Dao.IDao;

import Dao.VO.Book;

import java.sql.SQLException;
import java.util.List;


public interface CartDao {
    public void init();
    //添加操作
    public void insert(Book book);
    //删除操作
    public void delete(int id);
    //更改操作
    public void updateBuyNum(Book book);
    //查询操作
    //书名|作者|描述模糊查询
    public List<Book> queryByNAD(String keyWords);
    //书ID查询
    public List<Book>  queryById(int bookId);
   //查询全部
    public List<Book> queryAllBook();
}
