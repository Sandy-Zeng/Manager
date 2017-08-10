package chinasoft.com.util;

import org.litepal.LitePalApplication;
import org.xutils.x;

/**
 * Created by Ｓａｎｄｙ on 2017/8/2.
 */

public class MyApplication extends LitePalApplication {
    private static MyApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
    public static MyApplication getInstance(){
        // 因为我们程序运行后，Application是首先初始化的，如果在这里不用判断instance是否为空
        return instance;
    }

}
