package chinasoft.com.logindemo;

import android.content.Context;

/**
 * Created by zxl on 2017/8/5.
 */

public class DensityUtil {
    private DensityUtil(){}
    public static int dp2px(Context context, float dpValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return(int)(dpValue*scale+0.5f);
    }
}
