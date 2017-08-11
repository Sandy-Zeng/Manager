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


@ContentView(R.layout.activity_privacy)
public class PrivacyActivity extends AppCompatActivity {
    @ViewInject(R.id.through_name)
    private CheckBox through_name;
    @ViewInject(R.id.through_phone)
    private CheckBox through_phone;
    private SharedPreferences sp;
    Boolean myname=true;
    Boolean myphone=false;
    @ViewInject(R.id.back)
    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_privacy);
        x.view().inject(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sp=getSharedPreferences("privacy_state",MODE_PRIVATE);
        myname=sp.getBoolean("name",true);
        myphone=sp.getBoolean("phone",true);
        through_name.setChecked(myname);
        through_phone.setChecked(myphone);

        through_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        through_phone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("name",through_name.isChecked());
        editor.putBoolean("phone",through_phone.isChecked());
        editor.commit();

    }
}
