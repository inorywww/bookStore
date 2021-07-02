package Dao.VO;

public class Order {
    private int orderID;
    private String account;
    private String status;
    private int buy_num;
    private float totalPrice;
    private String booksID;
    private String booksNum;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getBuy_num() {
        return buy_num;
    }

    public void setBuy_num(int buy_num) {
        this.buy_num = buy_num;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBooksID() {
        return booksID;
    }

    public void setBooksID(String booksID) {
        this.booksID = booksID;
    }

    public String getBooksNum() {
        return booksNum;
    }

    public void setBooksNum(String booksNum) {
        this.booksNum = booksNum;
    }
}
