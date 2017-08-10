package chinasoft.com.dbutil;

import android.database.sqlite.SQLiteDatabase;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

import chinasoft.com.vo.Customer;

/**
 * Created by Ｓａｎｄｙ on 2017/8/6.
 */

public class CustomerHelper {
    private SQLiteDatabase db;

    public  CustomerHelper(){

        db = Connector.getWritableDatabase();
    }


    public boolean hasCustomer(String username){
        List<Customer> customers = DataSupport.where("username = ?",username ).find(Customer.class);
        if(customers.size()==0)
            return false;
        return true;
    }

    public void insert(String username)
    {
        Customer customer=new Customer(username);
        customer.save();
    }

    public boolean delete(String username)
    {
        List<Customer> customers = DataSupport.where("username = ?",username).find(Customer.class);
        if(customers.size()!=0)
        {
            customers.get(0).delete();
            return true;
        }
        return false;
    }

    public Customer find(String username)
    {
        List<Customer> customers = DataSupport.where("username = ?",username).find(Customer.class);
        return customers.get(0);
    }

    public List<Customer> findAll()
    {
        return DataSupport.findAll(Customer.class);
    }


    public void closeDB()
    {
        db.close();
    }


}
