package chinasoft.com.vo;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ｓａｎｄｙ on 2017/8/6.
 */

public class Customer extends DataSupport {
    private Integer id;
    private String username;//用户名
    private List<Record> recordList;//搜索记录
    private List<Cart> cartList;//购物车
    private List<Like> likeList;//收藏列表
    private List<History> historyList;//足迹

    public Customer(){
        recordList=new ArrayList<>();
        cartList=new ArrayList<>();
        likeList=new ArrayList<>();
        historyList=new ArrayList<>();
    }
    public Customer(String username) {
        this.username = username;
        recordList=new ArrayList<>();
        cartList=new ArrayList<>();
        likeList=new ArrayList<>();
        historyList=new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Record> getRecordList() {
        return DataSupport.where("customer_id = ?", String.valueOf(id)).find(Record.class);
    }

    public void setRecordList(List<Record> recordList) {
        this.recordList = recordList;
    }

    public List<Cart> getCartList() {
        return DataSupport.where("customer_id = ?", String.valueOf(id)).find(Cart.class);
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public List<Like> getLikeList() {
        return DataSupport.where("customer_id = ?", String.valueOf(id)).find(Like.class);
    }

    public void setLikeList(List<Like> likeList) {
        this.likeList = likeList;
    }

    public List<History> getHistoryList() {
        return DataSupport.where("customer_id = ?", String.valueOf(id)).find(History.class);
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }
}
