package chinasoft.com.logindemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by zxl on 2017/8/5.
 */

public class PreviewVideoView extends VideoView{
    public PreviewVideoView(Context context) {
        super(context);
    }

    public PreviewVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreviewVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(w, (int) (w / 0.56f));
    }
}
