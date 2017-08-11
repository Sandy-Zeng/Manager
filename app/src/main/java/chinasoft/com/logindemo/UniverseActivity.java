package chinasoft.com.logindemo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

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
    @ViewInject(R.id.back)
    private ImageView back;
    Boolean mylocation=true;
    Boolean mymessage=true;
    Boolean mycache=true;
    private SharedPreferences sp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_universe);
        x.view().inject(this);

        sp=getSharedPreferences("universe_state",MODE_PRIVATE);
        mylocation=sp.getBoolean("location",true);
        mymessage=sp.getBoolean("message",true);
        mycache=sp.getBoolean("cache",true);
        location.setChecked(mylocation);
        message.setChecked(mymessage);
        cache.setChecked(mycache);

        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        message.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        cache.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("location",location.isChecked());
        editor.putBoolean("message",message.isChecked());
        editor.putBoolean("cache",cache.isChecked());
        editor.commit();

    }
}
