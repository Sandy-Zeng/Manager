package chinasoft.com.dbutil;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

import chinasoft.com.vo.Cart;
import chinasoft.com.vo.Customer;

/**
 * Created by Ｓａｎｄｙ on 2017/8/8.
 */

public class CartHelper {
    private SQLiteDatabase db;
    public CartHelper(){
        db = Connector.getWritableDatabase();
    }
    public void add(Integer pid,Integer num,String username)
    {
        CustomerHelper customerHelper = new CustomerHelper();
        Customer customer=customerHelper.find(username);
        Cart cart = new Cart(pid,num,customer);
        cart.save();
    }
    public void delete(Integer cid)
    {
        Cart cart = this.find(cid);
        cart.delete();
    }

    public void deleteAll(String username)
    {
        List<Cart> carts = this.findAll(username);
        for(int i=0;i<carts.size();i++)
        {
            carts.get(i).delete();
        }
    }


    public List<Cart> findAll(String username)
    {
        CustomerHelper customerHelper = new CustomerHelper();
        Customer customer=customerHelper.find(username);
        List<Cart> carts = DataSupport.where("customer_id = ?",Integer.toString(customer.getId())).find(Cart.class);
        return carts;
    }

    public Cart find(Integer cid)
    {
        Cart cart = DataSupport.find(Cart.class,cid);
        return cart;
    }

    public boolean hasCart(Integer pid)
    {
        List<Cart> carts = DataSupport.where("pid = ?",Integer.toString(pid)).find(Cart.class);
        if(carts.size()==0)
            return false;
        return true;
    }

    public Cart findByPid(Integer pid)
    {
        List<Cart> carts = DataSupport.where("pid = ?",Integer.toString(pid)).find(Cart.class);
        return carts.get(0);
    }


    public void update(int step,Integer pid)
    {
        Cart cart = this.findByPid(pid);
        ContentValues cv = new ContentValues();
        cv.put("num",cart.getNum()+step);
        DataSupport.update(Cart.class,cv,cart.getId());
    }



    public void close()
    {
        db.close();
    }


}
