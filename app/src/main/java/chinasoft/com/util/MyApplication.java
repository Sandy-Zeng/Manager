package chinasoft.com.util;

import org.litepal.LitePalApplication;
import org.xutils.x;

/**
 * Created by Ｓａｎｄｙ on 2017/8/2.
 */

public class MyApplication extends LitePalApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
