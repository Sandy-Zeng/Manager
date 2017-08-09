package chinasoft.com.vo;

import org.litepal.crud.DataSupport;

/**
 * Created by Ｓａｎｄｙ on 2017/8/6.
 */

public class Cart extends DataSupport {
    private Integer id;
    private Integer pid;//商品ID
    private Integer num;//商品的数量
    private Customer customer;//对应的顾客

    public Cart(Integer pid, Integer num, Customer customer) {
        this.pid = pid;
        this.num = num;
        this.customer = customer;
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

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
