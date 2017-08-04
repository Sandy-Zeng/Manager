package chinasoft.com.util;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Ｓａｎｄｙ on 2017/8/2.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
