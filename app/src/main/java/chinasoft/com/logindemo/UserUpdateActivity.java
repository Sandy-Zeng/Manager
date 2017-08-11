package chinasoft.com.logindemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_user_update)
public class UserUpdateActivity extends AppCompatActivity {
    @ViewInject(R.id.commit)
    private Button commit;
    @ViewInject(R.id.cancel)
    private Button cancel;
    @ViewInject(R.id.back)
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_update);
        x.view().inject(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_back=new Intent(UserUpdateActivity.this,ConfigActivity.class);
                startActivity(go_back);

            }
        });
    }
}
