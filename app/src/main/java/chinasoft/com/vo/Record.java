package chinasoft.com.vo;

import org.litepal.crud.DataSupport;

/**
 * Created by Ｓａｎｄｙ on 2017/8/6.
 */

public class Record extends DataSupport {
    private Integer id;
    private String name;
    private Customer customer;

    public Record(String name, Customer customer) {
        this.name = name;
        this.customer = customer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
