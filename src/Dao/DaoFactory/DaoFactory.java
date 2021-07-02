package Dao.DaoFactory;

import Dao.DaoImpl.*;
import Dao.IDao.*;


public class DaoFactory {
    public static BookDao getBookDaoInstance(){
        return new BookDaoImpl();
    }
    public static UserDao getUserDaoInstance(){
        return new UserDaoImpl();
    }
    public static CartDao getCartDaoInstance(){ return new CartDaoImpl(); }
    public static BookDetailDao getBookDetailInstance(){return new BookDetailImpl();}
    public static OrderDao getOrderInstance(){return new OrderImpl();}
}
