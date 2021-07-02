package Dao.IDao;

import Dao.VO.BookDetail;

import java.sql.SQLException;
import java.util.List;

public interface BookDetailDao {
    //添加操作
    public void insert(BookDetail book);
    //删除操作
    public void delete(int id);
    //更改操作
    public void update(BookDetail book);
    //查询操作
    //书ID查询
    public List<BookDetail>  queryById(int bookId);
    //所有书查询
    public List<BookDetail> queryAllBook() throws SQLException, ClassNotFoundException;
}
