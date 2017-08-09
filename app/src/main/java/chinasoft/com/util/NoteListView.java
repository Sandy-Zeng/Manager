package chinasoft.com.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zxl on 2017/8/6.
 */

public class NoteListView extends ListView {
    public NoteListView(Context context, AttributeSet attrs){
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
