package chinasoft.com.dbutil;

import android.database.sqlite.SQLiteDatabase;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

import chinasoft.com.vo.Customer;
import chinasoft.com.vo.Like;

/**
 * Created by Ｓａｎｄｙ on 2017/8/8.
 */

public class LikeHelper {
    private SQLiteDatabase db;
    public LikeHelper(){
        db = Connector.getWritableDatabase();
    }

    public void add(Integer pid, String username, String title, String place, String price)
    {
        CustomerHelper customerHelper = new CustomerHelper();
        Customer customer=customerHelper.find(username);
        Like like = new Like(pid, customer, price, title, place);
        like.save();
    }
    public void delete(Integer lid)
    {
        Like like = this.find(lid);
        like.delete();
    }

    public void deleteAll(String username)
    {
        List<Like> likes = this.findAll(username);
        for(int i=0 ;i<likes.size();i++)
        {
            likes.get(i).delete();
        }
    }

    public List<Like> findAll(String username)
    {
        CustomerHelper customerHelper = new CustomerHelper();
        Customer customer=customerHelper.find(username);
        List<Like> likes = DataSupport.where("customer_id = ?",Integer.toString(customer.getId())).find(Like.class);
        return likes;
    }

    public Like find(Integer lid)
    {
        Like likes = DataSupport.find(Like.class,lid);
        return likes;
    }

    public boolean hasLike(Integer pid)
    {
        List<Like> likes = DataSupport.where("pid = ?",Integer.toString(pid)).find(Like.class);
        if(likes.size()==0)
            return false;
        return true;
    }

    public void deleteByPid(Integer pid)
    {
        List<Like> likes = DataSupport.where("pid = ?",Integer.toString(pid)).find(Like.class);
        likes.get(0).delete();
    }


    public void close()
    {
        db.close();
    }

}
