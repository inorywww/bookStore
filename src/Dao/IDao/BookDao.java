package Dao.IDao;

import Dao.VO.Book;

import java.sql.SQLException;
import java.util.List;


public interface BookDao {
    public void init();
    //添加操作
    public void insert(Book book);
    //删除操作
    public void delete(int id);
    //更改操作
    public void update(Book book);
    public void updateBuyNum(Book book);
    //查询操作
    //书名|作者|描述模糊查询
    public List<Book> queryByNAD(String keyWords);
    //书ID查询
    public List<Book>  queryById(int bookId);
    //所有书查询
    public List<Book> queryAllBook() throws SQLException, ClassNotFoundException;
    //分类查询
    public List<Book> queryByCategory(String category);
    //后续有其他需求的操作再行添加
}
