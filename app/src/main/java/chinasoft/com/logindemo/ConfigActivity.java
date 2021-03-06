package chinasoft.com.logindemo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_config)
public class ConfigActivity extends AppCompatActivity {
    @ViewInject(R.id.back)
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_config);
        x.view().inject(this);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_config, container, false);
            return rootView;
        }
    }

    public void go(View view){
        switch(view.getId()){
            case R.id.user_msg:
                Intent go_user_msg=new Intent(ConfigActivity.this,UserUpdateActivity.class);
                startActivity(go_user_msg);
                break;
            case R.id.location:
                Intent go_location=new Intent(ConfigActivity.this,LocationActivity.class);
                startActivity(go_location);
                break;
            case R.id.universe:
                Intent go_universe=new Intent(ConfigActivity.this,UniverseActivity.class);
                startActivity(go_universe);
                break;
            case R.id.privacy:
                Intent go_privacy=new Intent(ConfigActivity.this,PrivacyActivity.class);
                startActivity(go_privacy);
                break;
            case R.id.about_us:
                Intent go_about_us=new Intent(ConfigActivity.this,AboutUsActivity.class);
                startActivity(go_about_us);
                break;
            case R.id.exit:
                Intent go_register = new Intent(ConfigActivity.this, ChangeActivity.class);
                startActivity(go_register);
                break;
        }
    }

}
