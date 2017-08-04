package chinasoft.com.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ｓａｎｄｙ on 2017/8/3.
 */

public class MyHelper extends SQLiteOpenHelper {


    public MyHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion==1&&newVersion==2){
            String sql="create table if not exists userinfo(username text not null primary key,pwd text not null)";
            db.execSQL(sql);
        }

    }
}
