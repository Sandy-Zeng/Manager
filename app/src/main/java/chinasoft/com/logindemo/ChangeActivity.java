package chinasoft.com.logindemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_change)
public class ChangeActivity extends Activity {

    @ViewInject(R.id.adminlogin)
    private Button adminlogin;
    @ViewInject(R.id.userlogin)
    private Button userlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
    public void change(View view){
        switch (view.getId()){
            case R.id.adminlogin:
                Intent intent1=new Intent(ChangeActivity.this,AdminMain.class);
                startActivity(intent1);
                break;
            case R.id.userlogin:
                Intent intent2=new Intent(ChangeActivity.this,login.class);
                startActivity(intent2);
                break;
        }
    }
}
