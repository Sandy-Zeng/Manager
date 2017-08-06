package chinasoft.com.logindemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.index)
public class IndexActivity extends AppCompatActivity {
    @ViewInject(R.id.user)
    private Button user;
    @ViewInject(R.id.admin)
    private Button admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.index);
        x.view().inject(this);

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_login=new Intent(IndexActivity.this,login.class);
                startActivity(go_login);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_admin=new Intent(IndexActivity.this,AdminLogin.class);
                startActivity(go_admin);
            }
        });
    }
}
