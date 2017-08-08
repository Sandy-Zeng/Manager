package chinasoft.com.dbutil;

import android.database.sqlite.SQLiteDatabase;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

import chinasoft.com.vo.Customer;
import chinasoft.com.vo.History;

/**
 * Created by Ｓａｎｄｙ on 2017/8/8.
 */

public class HistoryHelper {
    private SQLiteDatabase db;
    public HistoryHelper(){
        db = Connector.getWritableDatabase();
    }
    public void add(Integer pid,String username)
    {
        CustomerHelper customerHelper = new CustomerHelper();
        Customer customer=customerHelper.find(username);
        History history = new History(pid,customer);
        history.save();
    }
    public void delete(Integer hid)
    {
        History history = this.find(hid);
        history.delete();
    }

    public void deleteAll(String username)
    {
        List<History> history = this.findAll(username);
        for(int i=0 ;i<history.size();i++)
        {
            history.get(i).delete();
        }
    }

    public List<History> findAll(String username)
    {
        CustomerHelper customerHelper = new CustomerHelper();
        Customer customer=customerHelper.find(username);
        List<History> histories = DataSupport.where("customer_id = ?",Integer.toString(customer.getId())).find(History.class);
        return histories;
    }

    public History find(Integer hid)
    {
        History history = DataSupport.find(History.class,hid);
        return history;
    }

    public void close()
    {
        db.close();
    }


}
