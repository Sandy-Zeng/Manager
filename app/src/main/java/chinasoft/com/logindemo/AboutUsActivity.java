package chinasoft.com.logindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

@ContentView(R.layout.activity_about_us)
public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_about_us);
        x.view().inject(this);
    }
}
