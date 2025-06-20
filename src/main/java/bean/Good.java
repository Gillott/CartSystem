package bean;

public class Good {
    // 唯一标识
    private int g_id;
    private String good;
    private String brand;
    private Double price;

    public int getGid() {
        return g_id;
    }

    public void setGid(int g_id) {
        this.g_id = g_id;
    }

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

    public Good(int g_id, String good, String brand, Double price) {
        this.g_id = g_id;
        this.good = good;
        this.brand = brand;
        this.price = price;
    }

    public Good() {
    }
}
