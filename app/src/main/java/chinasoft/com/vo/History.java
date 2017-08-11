package chinasoft.com.vo;

import org.litepal.crud.DataSupport;

/**
 * Created by Ｓａｎｄｙ on 2017/8/6.
 */

public class History extends DataSupport {
    private Integer id;
    private Integer pid;
    private Customer customer;
    private String price;
    private String title;
    private String place;


    public History(Integer pid, Customer customer, String price, String title, String place) {
        this.pid = pid;
        this.customer = customer;
        this.price = price;
        this.title = title;
        this.place = place;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
