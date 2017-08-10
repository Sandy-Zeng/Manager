package chinasoft.com.logindemo;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_all_order)
public class AllOrderActivity extends Activity {
    @ViewInject(R.id.commit_order)
    private ListView commit_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_all_order);
        x.view().inject(this);
    }
}
