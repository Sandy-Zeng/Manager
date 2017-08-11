package chinasoft.com.logindemo;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@ContentView(R.layout.admin_login)
public class AdminLogin extends AppCompatActivity {
    @ViewInject(R.id.username)
    private TextView username;
    @ViewInject(R.id.password)
    private TextView password;
    @ViewInject(R.id.login)
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.admin_login);
        x.view().inject(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pwd = password.getText().toString();
                Request request = new Request.Builder().url("http://192.168.40.14:8080/dgManager/adminlogin!adminLogin_android?adminName=" + user + "&password=" + pwd)
                        .get()
                        .build();
                exec(request);

            }
        });
    }

    private void exec(Request request) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("info", "链接失败");
                /*Message message =new Message();
                message.what=1;
                message.obj ="ok";
                handler.sendMessage(message);*/
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i("info", "链接成功");
                String s = response.body().string();
                Log.i("info", s);
                Message message = new Message();
                message.what = 1;
                message.obj = s;
                handler.sendMessage(message);
            }
        });
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String result = (String) msg.obj;
            if (msg.what == 1) {
                //String result="ok";
                Log.i("info", result);
                if (result.equals("成功")) {
                    Intent intent = new Intent(AdminLogin.this, AdminMain.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(AdminLogin.this, "登录成功", Toast.LENGTH_SHORT).show();
                } else {
                    new AlertDialog.Builder(AdminLogin.this).setTitle(result)
                            .create().show();
                }
            }
        }

    };
}
