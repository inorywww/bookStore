package Dao.IDao;
import Dao.VO.Book;
import Dao.VO.User;

import java.util.List;

public interface UserDao {

    boolean register(User user);

    List<User> getAllUser();

    User getUserInfo(String account);

    void update(User user);

    void insert(User user);

    void delete(String account);

    //account|name模糊查询
    List<User> queryByAN(String keyWords);
}
