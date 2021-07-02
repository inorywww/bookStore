package Dao.VO;

public class BookDetail {
    private int id;
    private String name;
    private String author;
    private String press;
    private String pDate;
    private int pages;
    private float price;
    private String ISBN;
    private String category;
    private String introduction;
    private String url;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public void setpDate(String pDate) {
        this.pDate = pDate;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getPress() {
        return press;
    }

    public String getpDate() {
        return pDate;
    }

    public int getPages() {
        return pages;
    }

    public float getPrice() {
        return price;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getCategory() {
        return category;
    }

    public String getIntroduction() { return introduction; }

    public String getUrl() { return url; }
}
