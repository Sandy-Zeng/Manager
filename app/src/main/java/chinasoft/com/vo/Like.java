package chinasoft.com.vo;

import org.litepal.crud.DataSupport;

/**
 * Created by Ｓａｎｄｙ on 2017/8/6.
 */

public class Like extends DataSupport {
    private Integer id;
    private Integer  pid;
    private Customer customer;

    public Like(Integer pid, Customer customer) {
        this.pid = pid;
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

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
