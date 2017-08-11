package chinasoft.com.service;

/**
 * Created by Ｓａｎｄｙ on 2017/8/11.
 */

public class Order_Product {
    private String name;
    private Integer number;
    private String price;
    private Integer pid;

    public Order_Product(String name, Integer number, String price, Integer pid) {
        this.name = name;
        this.number = number;
        this.price = price;
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
