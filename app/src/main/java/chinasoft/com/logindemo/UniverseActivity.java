package chinasoft.com.logindemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_universe)
public class UniverseActivity extends AppCompatActivity {
    @ViewInject(R.id.location)
    private CheckBox location;
    @ViewInject(R.id.message)
    private CheckBox message;
    @ViewInject(R.id.cache)
    private CheckBox cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_universe);
        x.view().inject(this);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info","location");
            }
        });

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info","message");
            }
        });

        cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("info","cache");
            }
        });
    }
}
