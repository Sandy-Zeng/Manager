package chinasoft.com.dbutil;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.crud.DataSupport;

import java.util.List;

import chinasoft.com.vo.Customer;
import chinasoft.com.vo.Record;

import static org.litepal.tablemanager.Connector.getWritableDatabase;

/**
 * Created by Ｓａｎｄｙ on 2017/8/6.
 */

public class RecordSQLiteOpenHelper  {
    private SQLiteDatabase db;
    public RecordSQLiteOpenHelper()
    {
        db = getWritableDatabase();
    }
    public boolean hasRecord(String tempName) {
        //从Record这个表里找到name=tempName的id
        Cursor cursor = db.rawQuery(
                "select id as _id,name from record where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    public void insert(String tempName,String user) {
        db = getWritableDatabase();
        List<Customer> customers= DataSupport.where("username = ?",user ).find(Customer.class);
        Customer customer=customers.get(0);
        Record record=new Record(tempName,customer);
        record.save();
    }

    public  void delete(String user) {
        CustomerHelper customerHelper = new CustomerHelper();
        Customer customer=customerHelper.find(user);
        List<Record> recordList = customer.getRecordList();
        for(int i=0;i<recordList.size();i++)
        {
           // List<Record> records=DataSupport.where("name = ?",recordList.get(i).getName()).find(Record.class);
            recordList.get(i).delete();
        }
    }

    public List<Record> findAll(String username)
    {
        CustomerHelper customerHelper = new CustomerHelper();
        Customer customer = customerHelper.find(username);
        //Log.i("info",Integer.toString(customer.getRecordList().size()));
        //Log.i("info",Integer.toString(customer.getId()));
        //int size= customer.getRecordList().size();
        return customer.getRecordList();
    }


    public void closeDB()
    {
        db.close();
    }


}
