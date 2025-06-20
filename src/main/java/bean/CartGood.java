package bean;

public class CartGood {
    private String good;
    private String brand;
    private Double price;
    private int num;

    public String getGood() {
        return good;
    }

    public void setGood(String good) {
        this.good = good;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getNum() { return num; }

    public void setNum(int num) { this.num = num; }

    public CartGood(String good, String brand, Double price, int num) {
        this.good = good;
        this.brand = brand;
        this.price = price;
        this.num = num;
    }

    public CartGood() {
    }
}
