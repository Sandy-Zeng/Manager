package chinasoft.com.logindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

@ContentView(R.layout.activity_location)
public class LocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_location);
        x.view().inject(this);
    }
}
